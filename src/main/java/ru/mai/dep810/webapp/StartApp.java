package ru.mai.dep810.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "ru.mai.dep810.webapp")
public class StartApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(StartApp.class, args);
    }
}
