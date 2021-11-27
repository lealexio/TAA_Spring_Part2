package com.tp3.part1.store;

import com.tp3.part1.bank.IBank;
import com.tp3.part1.provider.IProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class Store implements IFastLane, ILane, IJustHaveALook {
    @Autowired
    public IBank bank;

    @Autowired
    public IProvider provider;

    public HashMap<String, Integer> productPrices;
    public HashMap<String, Integer> productQuantity;
    public HashMap<String, Integer> userCart;

    @Value("${storeAccountNumber}")
    int storeBankAccount;

    public Store() {
        this.productPrices = new HashMap<String, Integer>();
        this.productQuantity = new HashMap<String, Integer>() ;
        this.userCart = new HashMap<String, Integer>() ;

        // Add article
        this.productPrices.put("coke",1);
        this.productQuantity.put("coke",20);

        // Add article
        this.productPrices.put("nutella",4);
        this.productQuantity.put("nutella",10);
    }

    @Override
    public boolean oneShotOrder(String productName, int quantity, int clientBankAccount) {
        if (this.isAvailable(productName, quantity) && bank.transfer(clientBankAccount, this.storeBankAccount, quantity * this.getPrice(productName))){
            // Remove in productQuantity
            this.productQuantity.put(productName, this.productQuantity.get(productName) - quantity);
            System.out.println("SUCCESSFUL PURCHASE");
            System.out.println("You have purchased  " + quantity + " x " + productName);
            this.providerRestock();
            return true;
        }

        else{
            System.out.println("PURCHASE FAILED");
            return false;
        }
    }

    @Override
    public int getPrice(String productName) {
        return this.productPrices.getOrDefault(productName, 0);
    }


    @Override
    public boolean isAvailable(String productName, int quantity) {
        return this.productQuantity.getOrDefault(productName, 0) >= quantity && quantity > 0;
    }

    @Override
    public boolean addItemToCart(String productName, int quantity) {
        if(this.productQuantity.getOrDefault(productName, 0) < quantity || quantity < 1){
            System.out.println("Can't add item to cart because product <" + productName + "> with quantity <" + quantity + "> isn't available.");
            return false;
        }
        else{
            this.userCart.put(productName, this.userCart.getOrDefault(productName, 0) + quantity);
            return true;
        }
    }

    @Override
    public void removeItemFromCart(String productName, int quantity) {
        if (quantity < 0){
            System.out.println("WARNING : Quantity to remove must be positive.");
            return;
        }
        int quantityOfItem = this.userCart.getOrDefault(productName ,0);
        if (quantityOfItem <=0 || quantityOfItem <= quantity){
            this.userCart.remove(productName);
        }
        else {
            this.userCart.put(productName, this.userCart.get(productName) - quantity);
        }
    }

    @Override
    public boolean pay(int clientBankAccount) {
        // Check availability and get total for cart
        int transactionAmount = 0;
        if (this.userCart.isEmpty()){
            System.out.println("PURCHASE FAILED");
            System.out.println("Empty Cart");
            return false;
        }
        for (Map.Entry<String, Integer> item : this.userCart.entrySet()) {
            transactionAmount += this.getPrice(item.getKey()) * item.getValue();
            // If an item isn't available cancel paiement
            if (!isAvailable(item.getKey(), item.getValue())){
                System.out.println("PURCHASE FAILED");
                System.out.println("Can't finish payment because product <" + item.getKey() + "> with quantity <" + item.getValue() + "> isn't available.");
                return false;
            }
        }
        // Do paiement when all items are available
        // Return false if paiement is refused
        if (!bank.transfer(clientBankAccount, this.storeBankAccount, transactionAmount)){
            System.out.println("PURCHASE FAILED");
            return false;
        }

        System.out.println("SUCCESSFUL PURCHASE");
        System.out.println("You have purchased :");
        // Remove items from store inventory when paiement accepted
        for (Map.Entry<String, Integer> item : this.userCart.entrySet()) {
            System.out.println("- " + item.getKey() + " x " + item.getValue());
            this.productQuantity.put(item.getKey(), this.productQuantity.get(item.getKey()) - item.getValue());
        }

        // Clear client cart
        this.userCart.clear();

        this.providerRestock();

        return true;
    }

    public void providerRestock(){
        if (this.productQuantity.getOrDefault("coke",0) < 15){
            System.out.println("PROVIDER RESTOCK : Add 10 coke to store");
            System.out.println("PROVIDER : last stock " + this.productQuantity.getOrDefault("coke",0));
            provider.order("coke", 10, this.productQuantity);
            System.out.println("PROVIDER : new stock " + this.productQuantity.getOrDefault("coke",0));
        }
        if (this.productQuantity.getOrDefault("nutella",0) < 5){
            System.out.println("PROVIDER RESTOCK : Add 5 nutella to store");
            System.out.println("PROVIDER : last stock " + this.productQuantity.getOrDefault("nutella",0));
            provider.order("nutella", 5, this.productQuantity);
            System.out.println("PROVIDER : new stock " + this.productQuantity.getOrDefault("nutella",0));
        }

    }

}
