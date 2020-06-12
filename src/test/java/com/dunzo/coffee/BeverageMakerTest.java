package com.dunzo.coffee;

import com.dunzo.coffee.operation.BeverageMaker;
import com.dunzo.coffee.operation.ContentIndicator;
import com.dunzo.coffee.operation.MachineContentManager;
import com.dunzo.coffee.vo.Machine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;

public class BeverageMakerTest {
    
    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void test1() throws IOException, InterruptedException {
        Machine machine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/t1.txt")), Machine.class);
        MachineContentManager.addToMachine(machine.getItems());
        Thread contentIndicatorThread = new Thread(new ContentIndicator(machine.getItems().keySet()));
        contentIndicatorThread.setName("CONTENT_INDICATOR");
        contentIndicatorThread.start();
        ForkJoinPool forkJoinPool = new ForkJoinPool(machine.getOutlet().getNumberOfOutlets());
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_coffee"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"green_tea"));
        Thread.sleep(10000);
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"black_tea"));
    }
    
    @Test
    public void test2() throws IOException, InterruptedException {
        Machine machine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/t1.txt")), Machine.class);
        MachineContentManager.addToMachine(machine.getItems());
        Thread contentIndicatorThread = new Thread(new ContentIndicator(machine.getItems().keySet()));
        contentIndicatorThread.setName("CONTENT_INDICATOR");
        contentIndicatorThread.start();
        ForkJoinPool forkJoinPool = new ForkJoinPool(machine.getOutlet().getNumberOfOutlets());
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"black_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"green_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_coffee"));
    }
    
    @Test
    public void test3() throws IOException, InterruptedException {
        Machine machine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/t1.txt")), Machine.class);
        MachineContentManager.addToMachine(machine.getItems());
        Thread contentIndicatorThread = new Thread(new ContentIndicator(machine.getItems().keySet()));
        contentIndicatorThread.setName("CONTENT_INDICATOR");
        contentIndicatorThread.start();
        ForkJoinPool forkJoinPool = new ForkJoinPool(machine.getOutlet().getNumberOfOutlets());
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_coffee"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"black_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"green_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_tea"));
    }
    
    @Test
    public void test4() throws IOException, InterruptedException {
        Machine machine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/t1.txt")), Machine.class);
        MachineContentManager.addToMachine(machine.getItems());
        Thread contentIndicatorThread = new Thread(new ContentIndicator(machine.getItems().keySet()));
        contentIndicatorThread.setName("CONTENT_INDICATOR");
        contentIndicatorThread.start();
        ForkJoinPool forkJoinPool = new ForkJoinPool(machine.getOutlet().getNumberOfOutlets());
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"black_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"green_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_coffee"));
        MachineContentManager.addToMachine(Collections.singletonMap("hot_water",200));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_tea"));
    }
    
    @Test
    public void test5() throws IOException, InterruptedException {
        Machine machine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/t1.txt")), Machine.class);
        MachineContentManager.addToMachine(machine.getItems());
        Thread contentIndicatorThread = new Thread(new ContentIndicator(machine.getItems().keySet()));
        contentIndicatorThread.setName("CONTENT_INDICATOR");
        contentIndicatorThread.start();
        MachineContentManager.addToMachine(Collections.singletonMap("hot_water",10000));
        MachineContentManager.addToMachine(Collections.singletonMap("green_mixture",50));
        ForkJoinPool forkJoinPool = new ForkJoinPool(machine.getOutlet().getNumberOfOutlets());
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"black_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"green_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_coffee"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"green_tea"));
    }
    
    
    @Test
    public void test6() throws IOException, InterruptedException {
        Machine machine = objectMapper.readValue((Utils.getFileAsString("src/main/resources/t1.txt")), Machine.class);
        MachineContentManager.addToMachine(machine.getItems());
        Thread contentIndicatorThread = new Thread(new ContentIndicator(machine.getItems().keySet()));
        contentIndicatorThread.setName("CONTENT_INDICATOR");
        contentIndicatorThread.start();
        MachineContentManager.addToMachine(Collections.singletonMap("hot_water",10000));
        ForkJoinPool forkJoinPool = new ForkJoinPool(machine.getOutlet().getNumberOfOutlets());
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"black_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"green_tea"));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"hot_coffee"));
        MachineContentManager.addToMachine(Collections.singletonMap("green_mixture",50));
        forkJoinPool.execute(new BeverageMaker(machine.getBeverages(),"green_tea"));
    }
    
    
}
