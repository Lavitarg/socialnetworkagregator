package instagram.service;

import instagram.entity.User;

public interface RegistrationService {
    boolean saveUnique(User user);
    boolean checkIfUserExist(String name);
    void saveUser(User user);
}
