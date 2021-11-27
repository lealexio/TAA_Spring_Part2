package com.tp3.part1.bank;

import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class Bank implements IBank {

    public HashMap<Integer, Integer> bankAccounts;

    public Bank() {
        this.bankAccounts = new HashMap<Integer, Integer>();
        // Add cash to shop bank account 1
        this.bankAccounts.put(1,100);
        // Add cash to client account 2
        this.bankAccounts.put(2,100);
    }

    @Override
    public boolean transfer(int from, int to, int amount) {
        if(this.bankAccounts.getOrDefault(from,0) < amount){
            System.out.println("PAYMENT REFUSED");
            System.out.println("Sender with account id=" + from + " does not have enough money on its account for transaction.");
            return false;
        }
        else if(amount < 0){
            System.out.println("PAYMENT REFUSED");
            System.out.println("Negative transaction not allowed");
            return false;
        }
        else{
            this.bankAccounts.put(from, this.bankAccounts.getOrDefault(from,0) - amount);
            this.bankAccounts.put(to, this.bankAccounts.getOrDefault(to,0) + amount);
            System.out.println("PAYMENT ACCEPTED");
            System.out.println("Send " + amount + "€ from account id=" + from + " to account id=" + to);
            System.out.println("New account balance for id=" + from + " -> "+ this.bankAccounts.getOrDefault(from,0) +"€");
            System.out.println("New account balance for id=" + to + " -> "+ this.bankAccounts.getOrDefault(to,0) +"€");
            return true;
        }
    }

    @Override
    public void removeBankAccount(int id) {
        this.bankAccounts.remove(id);
    }

    @Override
    public void addBankAccount(int id, int amount) {
        if(amount<0){
            System.out.println("Cant add account with negative bank amount");
        }
        else this.bankAccounts.put(id, amount);
    }

    @Override
    public int getBankAccountAmount(int id) {
        return this.bankAccounts.getOrDefault(id,0);
    }


}
