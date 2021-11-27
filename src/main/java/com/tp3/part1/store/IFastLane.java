package com.tp3.part1.store;

public interface IFastLane {
    /**
     * Do instant buy on an item
     * @param productName of desired article
     * @param quantity of desired article
     * @param clientBankAccount of client
     * @return true if transaction is finished
     */
    boolean oneShotOrder(String productName, int quantity, int clientBankAccount);
}
