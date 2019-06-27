package vk.service;

import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService{
    void authorize(String code);
}