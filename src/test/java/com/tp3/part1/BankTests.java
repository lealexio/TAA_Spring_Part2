package com.tp3.part1;

import com.tp3.part1.bank.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BankTests {

    @Autowired
    private Bank bank;

    @Test
    void contextLoads() {
        assertThat(bank).isNotNull();
    }

    @Test
    void transfer() {
        bank.bankAccounts.clear();
        bank.bankAccounts.put(1, 100);
        bank.bankAccounts.put(2, 100);
        assertThat(bank.transfer(1, 2, 50)).isEqualTo(true);
        assertThat(bank.bankAccounts.get(1)).isEqualTo(50);
        assertThat(bank.bankAccounts.get(2)).isEqualTo(150);
    }

    @Test
    void transferNegativeAmount() {
        bank.bankAccounts.clear();
        bank.bankAccounts.put(1, 100);
        bank.bankAccounts.put(2, 100);
        assertThat(bank.transfer(1, 2, -50)).isEqualTo(false);
        assertThat(bank.bankAccounts.get(1)).isEqualTo(100);
        assertThat(bank.bankAccounts.get(2)).isEqualTo(100);
    }

    @Test
    void transferWithoutMoney() {
        bank.bankAccounts.clear();
        bank.bankAccounts.put(1, 0);
        bank.bankAccounts.put(2, 100);
        assertThat(bank.transfer(1, 2, 50)).isEqualTo(false);
        assertThat(bank.bankAccounts.get(1)).isEqualTo(0);
        assertThat(bank.bankAccounts.get(2)).isEqualTo(100);
    }

    @Test
    void transferWithoutBankAccount() {
        bank.bankAccounts.clear();
        assertThat(bank.transfer(1, 2, 50)).isEqualTo(false);
    }

    @Test
    void transferToNewBankAccount() {
        bank.bankAccounts.clear();
        bank.bankAccounts.put(1, 100);
        assertThat(bank.transfer(1, 2, 50)).isEqualTo(true);
        assertThat(bank.bankAccounts.get(1)).isEqualTo(50);
        assertThat(bank.bankAccounts.get(2)).isEqualTo(50);
    }

    @Test
    void getAccountValid(){
        bank.bankAccounts.clear();
        bank.bankAccounts.put(1, 100);
        assertThat(bank.getBankAccountAmount(1)).isEqualTo(100);
    }

    @Test
    void getUndeclaredAccount(){
        bank.bankAccounts.clear();
        assertThat(bank.getBankAccountAmount(1000)).isEqualTo(0);
    }

    @Test
    void addBankAccount(){
        bank.bankAccounts.clear();
        bank.addBankAccount(1,100);
        assertThat(bank.getBankAccountAmount(1)).isEqualTo(100);
    }
}
