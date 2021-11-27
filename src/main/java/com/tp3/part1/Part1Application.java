package com.tp3.part1;

import com.tp3.part1.client.IRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Part1Application implements CommandLineRunner {
    @Autowired
    private IRun irun;

    public void run(String... args) {
        this.irun.run();
    }

    public static void main(String[] args) {
        SpringApplication.run(Part1Application.class, args);
    }

}
