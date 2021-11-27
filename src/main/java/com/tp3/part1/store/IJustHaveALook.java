package com.tp3.part1.store;

public interface IJustHaveALook {

    /**
     * Get price of an article
     * @param productName name of product
     * @return price of product
     */
    int getPrice(String productName);

    /**
     * Check if product is available
     * @param productName name of product
     * @param quantity of product
     * @return if product is available in desired quantity
     */
    boolean isAvailable(String productName, int quantity);
}
