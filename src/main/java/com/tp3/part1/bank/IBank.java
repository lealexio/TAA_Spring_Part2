package com.tp3.part1.bank;

public interface IBank {

    /**
     * Transfer of money between accounts
     * @param from id of sender account
     * @param to id of receiver account
     * @param amount of transaction
     * @return true if transaction is accepted, else false
     */
    boolean transfer(int from, int to, int amount);

    /**
     * Remove bank account
     * @param id of account to remove
     */
    void removeBankAccount(int id);

    /**
     * Add bank account or overwrite one
     * @param id of account to add/overwrite
     * @param amount of bank account
     */
    void addBankAccount(int id, int amount);

    /**
     * Get bank account amount
     * @param id of bank account
     * @return amount of bank account
     */
    int getBankAccountAmount(int id);
}
