package org.tc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.tc")
public class TCApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TCApplication.class, args);
        System.out.println("Let's inspect the beans provided by Spring Boot:");
    }
}

