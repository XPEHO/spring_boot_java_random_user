package com.xpeho.spring_boot_java_random_user;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJavaRandomUserApplication {

    static void main(String[] args) {
        Dotenv.configure()
                .ignoreIfMissing()
                .load()
                .entries()
                .forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(SpringBootJavaRandomUserApplication.class, args);
    }

}
