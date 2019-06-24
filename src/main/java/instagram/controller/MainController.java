package instagram.controller;


import instagram.dao.InstaProfileRepo;
import instagram.dao.SubscribersRepo;
import instagram.model.*;
import instagram.service.FollowersDownLoader;
import instagram.service.InstagramFollowersWorker;
import instagram.service.InstagramMediaWorker;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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