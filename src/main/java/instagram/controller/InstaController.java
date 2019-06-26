package instagram.controller;

import instagram.repository.InstaProfileRepo;
import instagram.repository.SubscribersRepo;
import instagram.model.*;
import instagram.service.InstagramFollowersWorker;
import instagram.service.InstagramMediaWorker;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.Name;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InstaController {
    @Autowired
    private InstagramMediaWorker instagramMediaWorker;
    @Autowired
    private InstagramFollowersWorker followersWorker;
    @Autowired
    private InstaProfileRepo instaProfileRepo;
    @Autowired
    private SubscribersRepo subscribersRepo;


    @GetMapping(value = "/filter")
    public String greeting(@AuthenticationPrincipal User user,Model model) throws IOException {
        InstaUserFilters instaUser = instaProfileRepo.findByUserId(user.getId());
        model.addAttribute("log",instaUser.getLogin());
        model.addAttribute("helper", new SubsChangeObject());
        return "filter";
    }


    @PostMapping(value = "/filter")
    public String greetingSubmit(@AuthenticationPrincipal User user,
                                 @ModelAttribute SubsChangeObject helper, Model model) throws IOException {
        String login = helper.getLogin();
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
        InstaUserFilters instaUser = instaProfileRepo.findByUserId(user.getId());
        model.addAttribute("login",instaUser.getLogin());
        List<String> checkedItems = foo.getCheckedItems();
        for(String nick: checkedItems){
            if(subscribersRepo.findByName(nick) == null){
                SubscribeItem item = new SubscribeItem(nick,instaUser);
                subscribersRepo.save(item);
            }
        }
        return "redirect:/instagram";
    }

    @GetMapping(value = "/tape")
    public String tape(@AuthenticationPrincipal User user,Model model) throws IOException {
        InstaUserFilters instaUser = instaProfileRepo.findByUserId(user.getId());
        if(instaUser == null){
            return "redirect:/newInstagramProfile";
        }
        List<SubscribeItem> subscribeItems = subscribersRepo.findAllByProfileId(instaUser.getId());
        List<Media> result = new ArrayList<>();
        for (SubscribeItem nick : subscribeItems) {
            List<Media> mediaList = instagramMediaWorker.getMedia(nick.getName());
            result.addAll(mediaList);
        }
        model.addAttribute("mediaList", result);
        return "tape";
    }

    @GetMapping(value = "/newInstagramProfile")
    public String newProfile(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("helper", new NameChanger());
        model.addAttribute("text","Add new Profile");
        return "loginChanger";
    }

    @PostMapping(value = "/newInstagramProfile")
    public String newProfileSaver(@AuthenticationPrincipal User user, @ModelAttribute NameChanger helper, Model model){
        InstaUserFilters instaUser = new InstaUserFilters(helper.getName(), user);
        instaProfileRepo.save(instaUser);
        return "redirect:/instagram";
    }

    @GetMapping(value = "/instagram")
    public String instagramMenu(@AuthenticationPrincipal User user,Model model){
        InstaUserFilters instaUser = instaProfileRepo.findByUserId(user.getId());
        if(instaUser == null){
            return "redirect:/newInstagramProfile";
        }
        model.addAttribute("name",instaUser.getLogin());
        return "instagramMenu";
    }
    @GetMapping(value = "/changeNick")
    public String changeNickName(@AuthenticationPrincipal User user,Model model){
        InstaUserFilters instaUser = instaProfileRepo.findByUserId(user.getId());
        if(instaUser == null){
            return "redirect:/newInstagramProfile";
        }
        model.addAttribute("helper", new NameChanger());
        model.addAttribute("text","Change login");
        return "loginChanger";
    }
    @PostMapping(value = "/changeNick")
    public String changeNickName(@AuthenticationPrincipal User user,@ModelAttribute NameChanger changer ,Model model){
        InstaUserFilters instaUser = instaProfileRepo.findByUserId(user.getId());
        instaUser.setLogin(changer.getName());
        instaProfileRepo.save(instaUser);
        return "redirect:/instagram";
    }
}
