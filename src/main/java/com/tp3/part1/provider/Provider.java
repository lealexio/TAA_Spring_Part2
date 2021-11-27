package com.tp3.part1.provider;

import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class Provider implements IProvider {


    public HashMap<String, Integer> productPrices;

    public Provider() {
        this.productPrices = new HashMap<String, Integer>();
    }

    @Override
    public int getPrice(String productName) {
        return productPrices.getOrDefault(productName,0);
    }

    @Override
    public boolean order(String productName, int quantity, HashMap<String, Integer> productQuantity) {
        // Remove order
        if((quantity < 0) && ((productQuantity.getOrDefault(productName, 0) + quantity) < 0)){
            // Negative stock after operation
            System.out.println("Can't remove " + quantity + " of " + productName + ", only " + productQuantity.getOrDefault(productName, 0) + " " + productName + " in stock.");
            return false;
        }
        productQuantity.put(productName, productQuantity.getOrDefault(productName, 0) + quantity);
        return true;
    }
}
