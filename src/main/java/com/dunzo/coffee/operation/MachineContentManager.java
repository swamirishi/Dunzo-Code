package com.dunzo.coffee.operation;

import com.dunzo.coffee.constants.Constants;
import com.dunzo.coffee.utils.Utils;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.dunzo.coffee.constants.Constants.EMPTY_STRING;

public class MachineContentManager {
    private static final MachineContents machineContents = new MachineContents();
    
    /**
     * Adds the Contents to the Machine
     *
     * @param contentsToBeAdded
     */
    public static void addToMachine(Map<String, Integer> contentsToBeAdded) {
        contentsToBeAdded.entrySet()
                .forEach(machineContent -> MachineContentManager.machineContents.addToMachine(machineContent.getKey(), machineContent.getValue()));
    }
    
    /**
     * @param beverageContents Gets Beverage of a particular composition
     * @return Status of Availability
     */
    public static Pair<String, Constants.MACHINE_INGREDIENT_STATUS> getBeverage(Map<String, Integer> beverageContents) {
        return machineContents.getBeverage(beverageContents);
    }
    
    public static Integer getQuantity(String ingredient) {
        return machineContents.getMachineContent(ingredient).map(AtomicInteger::get).orElse(0);
    }
    
    private static class MachineContents {
        private final Map<String, Pair<AtomicInteger, AtomicInteger>> machineContent = new ConcurrentHashMap<>();
        
        private int getContentAndQueueDiff(String ingredient) {
            return getMachineContent(ingredient).map(AtomicInteger::get).orElse(0) - getQueueContent(ingredient).map(AtomicInteger::get).orElse(0);
        }
        
        /**
         * @param ingredient Name of the Ingredient
         * @return Returns the Content of an Ingredient in the Machine
         */
        private Optional<AtomicInteger> getMachineContent(String ingredient) {
            return Optional.ofNullable(machineContent.get(ingredient)).map(Pair::getKey);
        }
        
        /**
         * @param ingredient Name of the Ingredient
         * @return Returns the Content of an Ingredient in Queue for Various Outlet Workers
         */
        private Optional<AtomicInteger> getQueueContent(String ingredient) {
            return Optional.ofNullable(machineContent.get(ingredient)).map(Pair::getValue);
        }
        
        /**
         * @param ingredient Name of the ingredient
         * @param quantity   Quantity of the ingredient
         * @return Status of the Availability in the Machine
         */
        private Constants.MACHINE_INGREDIENT_STATUS getIngredient(String ingredient, Integer quantity) {
            while (getMachineContent(ingredient).map(AtomicInteger::get).orElse(0) >= quantity) {
                if (getContentAndQueueDiff(ingredient) >= quantity) {
                    synchronized (machineContent.get(ingredient)) {
                        if (getContentAndQueueDiff(ingredient) >= quantity) {
                            getQueueContent(ingredient).get().addAndGet(quantity);
                            return Constants.MACHINE_INGREDIENT_STATUS.AVAILABLE;
                        }
                    }
                }
            }
            return machineContent.containsKey(ingredient) ? Constants.MACHINE_INGREDIENT_STATUS.INSUFFICIENT
                                                          : Constants.MACHINE_INGREDIENT_STATUS.UNAVAILABLE;
        }
        
        /**
         * Removes all the reserved ingredients in the queue for a particular outlet worker in case of Unavailibility of an Ingredient
         *
         * @param ingredient Name of the Ingredient
         * @param quantity   Quantity in Queue
         */
        private void rollback(String ingredient, Integer quantity) {
            synchronized (machineContent.get(ingredient)) {
                getQueueContent(ingredient).ifPresent(queueContent -> queueContent.addAndGet(-1 * quantity));
            }
        }
        
        /**
         * Removes all the required Ingredients from a Machine in the case where all ingredients are available
         *
         * @param beverage Composition of a Beverage
         */
        private void commit(Map<String, Integer> beverage) {
            for (Map.Entry<String, Integer> ingredient : beverage.entrySet()) {
                synchronized (machineContent.get(ingredient.getKey())) {
                    getMachineContent(ingredient.getKey()).ifPresent(queueContent -> queueContent.addAndGet(-1 * ingredient.getValue()));
                    getQueueContent(ingredient.getKey()).ifPresent(queueContent -> queueContent.addAndGet(-1 * ingredient.getValue()));
                }
            }
        }
        
        /**
         * Gets the user a beverage by removing corresponding components from the machine
         *
         * @param beverage Composition of a beverage
         * @return Name of the Ingredient in case unavailable and the status of the availability
         */
        public Pair<String, Constants.MACHINE_INGREDIENT_STATUS> getBeverage(Map<String, Integer> beverage) {
            Set<String> ingredientsReserved = new HashSet<>();
            for (Map.Entry<String, Integer> ingredient : beverage.entrySet()) {
                Constants.MACHINE_INGREDIENT_STATUS status = getIngredient(ingredient.getKey(), ingredient.getValue());
                if (Constants.MACHINE_INGREDIENT_STATUS.AVAILABLE.equals(status)) {
                    ingredientsReserved.add(ingredient.getKey());
                } else {
                    for (String ingredientReserved : ingredientsReserved) {
                        rollback(ingredientReserved, beverage.get(ingredientReserved));
                    }
                    return new Pair<>(ingredient.getKey(), status);
                }
            }
            commit(beverage);
            return new Pair<>(EMPTY_STRING, Constants.MACHINE_INGREDIENT_STATUS.AVAILABLE);
        }
        
        /**
         * Adds the ingredient to a Machine( No need for making the whole ingredient object value thread safe as only addition is happening
         *
         * @param ingredient Name of the Ingredient
         * @param quantity   Quantity of the ingredient
         */
        public void addToMachine(String ingredient, Integer quantity) {
            if (!Utils.isNull(machineContent.putIfAbsent(ingredient, new Pair<>(new AtomicInteger(quantity), new AtomicInteger(0))))) {
                getMachineContent(ingredient).ifPresent(queueContent->queueContent.addAndGet(quantity));
            }
        }
    }
}
