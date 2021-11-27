package com.tp3.part1.provider;

import java.util.HashMap;

public interface IProvider {

    /**
     * Get the price of an article
     * @param refArticle name of the article
     * @return price of the article, if return value is 0 article does not exists
     */
    int getPrice(String refArticle);

    /**
     * Reload store
     * @param productName of product to restock
     * @param quantity of product to restock
     * @param productQuantity stock of store to reload
     */
    boolean order(String productName, int quantity, HashMap<String, Integer> productQuantity);
}
