package org.tc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.tc")
@EnableAutoConfiguration
public class TCApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCApplication.class, args);
    }
}

