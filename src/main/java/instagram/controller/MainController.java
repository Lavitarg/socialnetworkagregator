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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private InstagramMediaWorker instagramMediaWorker;
    @Autowired
    private InstagramFollowersWorker followersWorker;
    @Autowired
    private InstaProfileRepo instaProfileRepo;
    @Autowired
    private SubscribersRepo subscribersRepo;

    @GetMapping(value = "/")
    public String main(Model model){
        return "main";
    }

    @GetMapping(value = "/greeting")
    public String greeting(Model model) throws IOException {
        model.addAttribute("helper", new SubsChangeObject());
        return "greeting";
    }


    @PostMapping(value = "/greeting")
    public String greetingSubmit(@AuthenticationPrincipal User user,
            @ModelAttribute SubsChangeObject helper, Model model) throws IOException {
        String login = helper.getLogin();
        InstaUserFilters profileByLogin = instaProfileRepo.findByLogin(login);
        if(profileByLogin == null){
            InstaUserFilters userFilters = new InstaUserFilters(login,user);
            instaProfileRepo.save(userFilters);
        }
        String password = helper.getPassword();
        List<String> subscribers = followersWorker.getFollowers(login, password);
        model.addAttribute("foo", new SubsFilter());
        model.addAttribute("subs", subscribers);
        return "check";
    }

    @PostMapping(value = "/processForm")
    public String processForm(@AuthenticationPrincipal User user,
            @ModelAttribute(value = "foo") SubsFilter foo, Model model) throws IOException {
        // Get value of checked item.
        InstaUserFilters instaUserFilters = instaProfileRepo.findByUserId(user.getId());
        model.addAttribute("login",instaUserFilters.getLogin());
        List<String> checkedItems = foo.getCheckedItems();
        for(String nick: checkedItems){
            if(subscribersRepo.findByName(nick) == null){
                SubscribeItem item = new SubscribeItem(nick,instaUserFilters);
                subscribersRepo.save(item);
            }
        }
        List<SubscribeItem> subscribeItems = subscribersRepo.findAllByProfileId(instaUserFilters.getId());
        List<Media> result = new ArrayList<>();
        for (SubscribeItem nick : subscribeItems) {
            List<Media> mediaList = instagramMediaWorker.getMedia(nick.getName());
            result.addAll(mediaList);
        }
        model.addAttribute("omg", result);
        return "start";

    }

}