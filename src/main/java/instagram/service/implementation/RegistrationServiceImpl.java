package instagram.service.implementation;

import instagram.entity.Role;
import instagram.entity.User;
import instagram.repository.InstagramProfileRepo;
import instagram.repository.UserRepo;
import instagram.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepo userRepo;
    private final InstagramProfileRepo profileRepo;


    @Override
    public boolean saveUnique(User user) {
        boolean exist = this.checkIfUserExist(user.getUsername());
        if(!exist){
            this.saveUser(user);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean checkIfUserExist(String name) {
        User userByName = userRepo.findByUsername(name);
        return (userByName != null);
    }

    @Override
    public void saveUser(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
    }
}
