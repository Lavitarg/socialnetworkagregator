package instagram.controller;

import instagram.entity.User;
import instagram.service.RepositoryWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final RepositoryWorker repositoryWorker;

    @GetMapping(value = "/registration")
    public String registration() {
        return "authpages/registration";
    }

    @PostMapping(value = "/registration")
    public String addUser(User user, Model model) {
        boolean exist = repositoryWorker.checkIfUserExist(user.getUsername());
        if (exist) {
            model.addAttribute("message", "User exists");
            return "authpages/registration";
        }
        repositoryWorker.saveUser(user);
        return "redirect:/login";
    }
}
