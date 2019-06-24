package instagram.controller;


import instagram.model.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String main(Model model){
        return "main";
    }

    @GetMapping(value = "/home")
    public String home(@AuthenticationPrincipal User user,
            Model model) {
        model.addAttribute("name",user.getUsername());
        return "home";
    }

}