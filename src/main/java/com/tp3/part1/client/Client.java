package com.tp3.part1.client;

import com.tp3.part1.store.IFastLane;
import com.tp3.part1.store.ILane;
import com.tp3.part1.store.IJustHaveALook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Client implements IRun {

    @Autowired
    IFastLane fastLane;
    @Autowired
    ILane lane;
    @Autowired
    IJustHaveALook look;


    @Value("${clientAccountNumber}")
    int clientAccountNumber;

    @Override
    public void run() {
        System.out.println("\nRun Scenario 1 : Fast order");
        System.out.println();
        fastLane.oneShotOrder("coke", 2, clientAccountNumber);

        System.out.println("\nRun Scenario 2 : Classic order");
        System.out.println();
        lane.addItemToCart("coke",1);
        lane.addItemToCart("nutella",4);
        lane.pay(clientAccountNumber);

        System.out.println("\nRun Scenario 3 : provider auto restock nutella");
        System.out.println();
        fastLane.oneShotOrder("nutella", 2, clientAccountNumber);

        System.out.println("\nRun Scenario 4 : provider auto restock coke");
        System.out.println();
        lane.addItemToCart("coke",3);
        lane.addItemToCart("coke",2);
        lane.pay(clientAccountNumber);
    }

}
