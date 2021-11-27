package com.tp3.part1;

import com.tp3.part1.store.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StoreTests {
    @Autowired
    public Store store;

    @Test
    void contextLoads() {
        assertThat(store).isNotNull();
    }

    //---------------------------
    //-------isAvailable---------
    //---------------------------

    @Test
    public void isAvailableValid(){
        store.productQuantity.clear();
        store.productQuantity.put("coke", 10);
        assertThat(store.isAvailable("coke",10)).isEqualTo(true);
        assertThat(store.isAvailable("coke",0)).isEqualTo(false);
    }

    @Test
    public void isAvailableEmptyStock(){
        store.productQuantity.clear();
        assertThat(store.isAvailable("coke",10)).isEqualTo(false);
    }

    @Test
    public void isAvailableNegative(){
        store.productQuantity.clear();
        store.productQuantity.put("coke", 10);
        assertThat(store.isAvailable("coke",-10)).isEqualTo(false);
    }

    //---------------------------
    //---------getPrice----------
    //---------------------------

    @Test
    void getPriceOfUndeclaredItem(){
        store.productPrices.clear();
        assertThat(store.getPrice("coke")).isEqualTo(0);
    }

    @Test
    void getPriceOfDeclaredItem(){
        store.productPrices.clear();
        store.productPrices.put("coke", 10);
        assertThat(store.getPrice("coke")).isEqualTo(10);
    }

    //---------------------------
    //-------addItemToCart-------
    //---------------------------

    @Test
    void addItemToCartValid(){
        store.productQuantity.clear();
        store.userCart.clear();
        store.productQuantity.put("coke", 10);
        assertThat(store.addItemToCart("coke", 10)).isEqualTo(true);
        assertThat(store.userCart.get("coke")).isEqualTo(10);

    }

    @Test
    void addItemToCartUndeclaredItem(){
        store.productQuantity.clear();
        store.userCart.clear();
        assertThat(store.addItemToCart("coke", 10)).isEqualTo(false);
        assertThat(store.userCart).isEmpty();
    }

    @Test
    void addItemToCartNegative(){
        store.productQuantity.clear();
        store.userCart.clear();
        store.productQuantity.put("coke", 10);
        assertThat(store.addItemToCart("coke", 5)).isEqualTo(true);
        assertThat(store.addItemToCart("coke", -2)).isEqualTo(false);
    }

    //---------------------------
    //-----removeItemFromCart----
    //---------------------------

    @Test
    void removeItemFromCartValid(){
        store.userCart.clear();
        store.userCart.put("coke", 10);
        store.removeItemFromCart("coke", 5);
        assertThat(store.userCart.get("coke")).isEqualTo(5);

    }

    @Test
    void removeItemFromCartUndeclaredItem(){
        store.userCart.clear();
        store.removeItemFromCart("coke", 10);
        assertThat(store.userCart).isEmpty();
    }

    @Test
    void removeItemFromCartNegative(){
        store.userCart.clear();
        store.removeItemFromCart("coke", -5);
        assertThat(store.userCart).isEmpty();
        store.userCart.put("coke", 5);
        store.removeItemFromCart("coke", -5);
        assertThat(store.userCart.get("coke")).isEqualTo(5);
    }

    //---------------------------
    //--------oneShotOrder-------
    //---------------------------

    @Test
    void oneShotOrderValid(){
        store.bank.addBankAccount(1,100);
        store.bank.addBankAccount(2,100);
        store.productQuantity.clear();
        store.productQuantity.put("coke", 50);
        store.productPrices.clear();
        store.productPrices.put("coke", 1);
        assertThat(store.oneShotOrder("coke", 10, 2)).isEqualTo(true);
        // Check new quantity
        assertThat(store.productQuantity.get("coke")).isEqualTo(40);
        // Check withdraw
        assertThat(store.bank.getBankAccountAmount(1)).isEqualTo(110);
        assertThat(store.bank.getBankAccountAmount(2)).isEqualTo(90);
    }

    @Test
    void oneShotOrderNoStock(){
        store.bank.addBankAccount(1,100);
        store.bank.addBankAccount(2,100);
        store.productQuantity.clear();
        store.productQuantity.put("coke", 1);
        store.productPrices.clear();
        store.productPrices.put("coke", 1);
        assertThat(store.oneShotOrder("coke", 10, 2)).isEqualTo(false);
        // Check new quantity
        assertThat(store.productQuantity.get("coke")).isEqualTo(1);
        // Check withdraw
        assertThat(store.bank.getBankAccountAmount(1)).isEqualTo(100);
        assertThat(store.bank.getBankAccountAmount(2)).isEqualTo(100);
    }

    @Test
    void oneShotOrderNotEnoughMoney(){
        store.bank.addBankAccount(1,100);
        store.bank.addBankAccount(2,1);
        store.productQuantity.clear();
        store.productQuantity.put("coke", 100);
        store.productPrices.clear();
        store.productPrices.put("coke", 1);
        assertThat(store.oneShotOrder("coke", 10, 2)).isEqualTo(false);
        // Check new quantity
        assertThat(store.productQuantity.get("coke")).isEqualTo(100);
        // Check withdraw
        assertThat(store.bank.getBankAccountAmount(1)).isEqualTo(100);
        assertThat(store.bank.getBankAccountAmount(2)).isEqualTo(1);
    }

    //---------------------------
    //-------------pay-----------
    //---------------------------

    @Test
    void payValid(){
        store.bank.addBankAccount(1,100);
        store.bank.addBankAccount(2,100);
        store.productQuantity.clear();
        store.productPrices.clear();
        store.userCart.clear();
        store.productQuantity.put("coke", 100);
        store.productPrices.put("coke", 1);
        assertThat(store.addItemToCart("coke",10)).isEqualTo(true);
        assertThat(store.pay(2)).isEqualTo(true);
        // Check new quantity
        assertThat(store.productQuantity.get("coke")).isEqualTo(90);
        // Check withdraw
        assertThat(store.bank.getBankAccountAmount(1)).isEqualTo(110);
        assertThat(store.bank.getBankAccountAmount(2)).isEqualTo(90);
    }

    @Test
    void payEmptyCart(){
        store.bank.addBankAccount(1,100);
        store.bank.addBankAccount(2,100);
        store.productQuantity.clear();
        store.productPrices.clear();
        store.userCart.clear();
        store.productQuantity.put("coke", 100);
        store.productPrices.put("coke", 1);
        assertThat(store.pay(2)).isEqualTo(false);
        // Check new quantity
        assertThat(store.productQuantity.get("coke")).isEqualTo(100);
        // Check withdraw
        assertThat(store.bank.getBankAccountAmount(1)).isEqualTo(100);
        assertThat(store.bank.getBankAccountAmount(2)).isEqualTo(100);
    }

    @Test
    void payNotEnoughMoney(){
        store.bank.addBankAccount(1,100);
        store.bank.addBankAccount(2,5);
        store.productQuantity.clear();
        store.productPrices.clear();
        store.userCart.clear();
        store.productQuantity.put("coke", 100);
        store.productPrices.put("coke", 1);
        assertThat(store.addItemToCart("coke",50)).isEqualTo(true);
        assertThat(store.pay(2)).isEqualTo(false);
        // Check new quantity
        assertThat(store.productQuantity.get("coke")).isEqualTo(100);
        // Check withdraw
        assertThat(store.bank.getBankAccountAmount(1)).isEqualTo(100);
        assertThat(store.bank.getBankAccountAmount(2)).isEqualTo(5);
    }

}
