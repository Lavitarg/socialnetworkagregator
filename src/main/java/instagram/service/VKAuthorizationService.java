package instagram.service;

import org.springframework.stereotype.Service;

@Service
public interface VKAuthorizationService {
    void authorize(String code);
}