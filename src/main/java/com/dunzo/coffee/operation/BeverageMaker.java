package com.dunzo.coffee.operation;

import com.dunzo.coffee.constants.Constants;
import com.dunzo.coffee.exception.BaseException;
import com.dunzo.coffee.vo.Machine;
import com.fasterxml.jackson.databind.ser.Serializers;
import javafx.util.Pair;

import java.util.Map;

public class BeverageMaker implements Runnable {
    private Map<String, Map<String,Integer>> beverages;
    private String beverageName;
    
    public BeverageMaker(Map<String,Map<String,Integer>> beverages, String beverageName) {
        this.beverages = beverages;
        this.beverageName = beverageName;
    }
    
    private void makeBeverage(){
        Pair<String, Constants.MACHINE_INGREDIENT_STATUS> status  = MachineContentManager.getBeverage(beverages.get(beverageName));
        switch (status.getValue()){
            case AVAILABLE: {
                synchronized (System.out){
                    System.out.println(beverageName +" is prepared");
                }
                break;
            }
            case UNAVAILABLE:{
                synchronized (System.out) {
                    System.out.println(beverageName +" cannot be prepared because "+status.getKey() +" is not available");
                }
                break;
            }
            case INSUFFICIENT:{
                synchronized (System.out) {
                    System.out.println(beverageName +" cannot be prepared because "+status.getKey() +" is not sufficient");
                }
                break;
            }
        }
    }
    
    @Override
    public void run() {
        if(beverages.containsKey(beverageName)){
            makeBeverage();
        }else{
            throw new BaseException("Beverage"+beverageName+" Not found");
        }
    }
}
