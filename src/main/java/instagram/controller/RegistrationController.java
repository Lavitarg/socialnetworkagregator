package instagram.controller;

import instagram.entity.User;
import instagram.service.RegistrationService;
import instagram.service.RepositoryWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping(value = "/registration")
    public String registration() {
        return "authpages/registration";
    }
    //TODO совместить строки 24 и 25
    @PostMapping(value = "/registration")
    public String addUser(User user, Model model) {
        if (!registrationService.saveUnique(user)) {
            model.addAttribute("message", "User exists");
            return "authpages/registration";
        }
        return "redirect:/login";
    }
}
