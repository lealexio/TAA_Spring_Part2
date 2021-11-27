package com.tp3.part1;

import com.tp3.part1.provider.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProviderTests {

    @Autowired
    private Provider provider;

    @Test
    void contextLoads() {
        assertThat(provider).isNotNull();
    }

    @Test
    void getPriceOfUndeclaredItem(){
        provider.productPrices.clear();
        assertThat(provider.getPrice("coke")).isEqualTo(0);
    }

    @Test
    void getPriceOfDeclaredItem(){
        provider.productPrices.clear();
        provider.productPrices.put("coke", 10);
        assertThat(provider.getPrice("coke")).isEqualTo(10);
    }

    @Test
    void addArticle(){
        HashMap<String, Integer> stock = new HashMap<>();
        assertThat(provider.order("coca", 10, stock)).isEqualTo(true);
        assertThat(stock.get("coca")).isEqualTo(10);
    }
    @Test
    void addArticleTwoTimes(){
        HashMap<String, Integer> stock = new HashMap<>();
        assertThat(provider.order("coca", 10, stock)).isEqualTo(true);
        assertThat(stock.get("coca")).isEqualTo(10);
        assertThat(provider.order("coca", 10, stock)).isEqualTo(true);
        assertThat(stock.get("coca")).isEqualTo(20);
    }

    @Test
    void removeArticle(){
        HashMap<String, Integer> stock = new HashMap<>();
        assertThat(provider.order("coca", 10, stock)).isEqualTo(true);
        assertThat(stock.get("coca")).isEqualTo(10);
        assertThat(provider.order("coca", -5, stock)).isEqualTo(true);
        assertThat(stock.get("coca")).isEqualTo(5);
    }

    @Test
    void removeMoreArticleThanStock(){
        HashMap<String, Integer> stock = new HashMap<>();
        assertThat(provider.order("coca", 10, stock)).isEqualTo(true);
        assertThat(stock.get("coca")).isEqualTo(10);
        assertThat(provider.order("coca", -50, stock)).isEqualTo(false);
        assertThat(stock.get("coca")).isEqualTo(10);
    }

}
