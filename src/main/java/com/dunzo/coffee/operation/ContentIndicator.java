package com.dunzo.coffee.operation;

import com.dunzo.coffee.operation.MachineContentManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

public class ContentIndicator implements Runnable{
    
    Set<String> ingredients;
    
    public ContentIndicator(Set<String> ingredients) {
        this.ingredients = ingredients;
    }
    
    @SneakyThrows
    @Override
    public void run() {
        while(true){
            for(String ingredient:this.ingredients){
                if(MachineContentManager.getQuantity(ingredient)==0){
                    synchronized (System.out){
//                        System.out.println("Ingredient: "+ingredient +" Empty !!!!! Please refill");
                    }
                }
            }
        }
    }
}
