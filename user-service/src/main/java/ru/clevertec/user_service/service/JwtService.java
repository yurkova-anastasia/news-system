package ru.clevertec.user_service.service;

public interface JwtService {


    String generateToken(String username);


    String getUserName(String token);
}
