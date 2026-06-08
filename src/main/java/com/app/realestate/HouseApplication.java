package com.app.realestate;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class HouseApplication {
    private static final Logger logger = LoggerFactory.getLogger(HouseApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HouseApplication.class, args);
        logger.info("House application has been started");
    }

    /**
     * Create the ChatClient bean
     * This is the main entry point for AI interactions
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        // it reads properties and creates a builder
        return builder.build();
    }
}
