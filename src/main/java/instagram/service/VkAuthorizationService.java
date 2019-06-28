package instagram.service;

import org.springframework.stereotype.Service;

@Service
public interface VkAuthorizationService {
    void authorize(String code);
}