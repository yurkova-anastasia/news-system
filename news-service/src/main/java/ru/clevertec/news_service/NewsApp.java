package ru.clevertec.news_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.clevertec.news_service.client.UserClient;

@SpringBootApplication
@EnableFeignClients(clients = UserClient.class)
public class NewsApp {

    public static void main(String[] args) {
        SpringApplication.run(NewsApp.class, args);
    }
}