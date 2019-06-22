package instagram.controller;


import instagram.service.FollowersDownLoader;
import instagram.model.Login_Helper;
import instagram.service.InstagramMediaWorker;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import instagram.model.SubsFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GreetingController {
    @Autowired
    InstagramMediaWorker instagramMediaWorker;

    @GetMapping(value = "/greeting")
    public String greeting(Model model) throws IOException {
        model.addAttribute("helper", new Login_Helper());
        return "greeting";
    }


    @PostMapping(value = "/greeting")
    public String greetingSubmit(
            @ModelAttribute Login_Helper helper, Model model) throws IOException {
        String login = helper.getLogin();
        String password = helper.getPassword();
        FollowersDownLoader fd = new FollowersDownLoader();
        List<String> subscribers = fd.getFollowers(login, password);
        model.addAttribute("foo", new SubsFilter());
        model.addAttribute("subs", subscribers);
        return "check";
    }

    @PostMapping(value = "/processForm")
    public String processForm(
            @ModelAttribute(value = "foo") SubsFilter foo, Model model) throws IOException {
        // Get value of checked item.

        List<String> checkedItems = foo.getCheckedItems();
        List<Media> result = new ArrayList<>();
        for (String nick : checkedItems) {
            List<Media> mediaList = instagramMediaWorker.getMedia(nick);
            result.addAll(mediaList);
        }
        model.addAttribute("omg", result);
        return "start";

    }

}