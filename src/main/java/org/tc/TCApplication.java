package org.tc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.tc")
public class TCApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCApplication.class, args);
    }
}

