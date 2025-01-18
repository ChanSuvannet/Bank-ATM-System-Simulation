package com.example.bankatmsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BankatmsystemApplication implements CommandLineRunner {

    @Autowired
    private Environment environment; // Injects environment properties

    public static void main(String[] args) {
        SpringApplication.run(BankatmsystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Get server port from environment or use default 8080
        String port = environment.getProperty("server.port", "8080");
        String message = String.format(
                "\u001B[32mSpring Boot application running on host: \u001B[34mhttp://localhost:%s\u001B[37m", port);
        System.out.println(message); // Print the message after startup
    }
}
