package instagram.controller;

import instagram.repository.UserRepo;
import instagram.entity.Role;
import instagram.entity.User;
import instagram.service.RepositoryWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final RepositoryWorker repositoryWorker;

    @GetMapping(value = "/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String addUser(User user, Model model) {
        boolean exist = repositoryWorker.checkIfUserExist(user.getUsername());
        if (exist) {
            model.addAttribute("message", "User exists");
            return "registration";
        }
        repositoryWorker.saveUser(user);
        return "redirect:/login";
    }
}
