package com.tp3.part1.store;

public interface ILane {
    /**
     * Add item to cart
     * @param productName of product to add
     * @param quantity of product to add
     * @return true if added
     */
    boolean addItemToCart(String productName, int quantity);

    /**
     * Remove item from cart
     * @param productName of product to add
     * @param quantity of product to add
     */
    void removeItemFromCart(String productName, int quantity);

    /**
     *
     * @param clientBankAccount id of user bank account
     * @return true if paiement is complete
     */
    boolean pay(int clientBankAccount);

}
