package instagram.controller;

import instagram.dao.UserRepo;
import instagram.model.Role;
import instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ValueConstants;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String addUser(User user, Model model){
        User userByName = userRepo.findByUsername(user.getUsername());
        if(userByName != null){
            model.addAttribute("message","User exists");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
