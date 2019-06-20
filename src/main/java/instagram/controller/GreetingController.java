package instagram.controller;

import instagram.service.FollowersDownLoader;
import instagram.service.Helper_pas;
import instagram.service.TestInstagram;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;


@Controller
public class GreetingController {

    @RequestMapping(value = "/greeting",method = RequestMethod.GET)
    public String greeting(Model model) throws IOException {
        model.addAttribute("helper", new Helper_pas());
        return "greeting";
    }
    @RequestMapping(value="/greeting", method= RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Helper_pas helper,
                                 @RequestParam(name = "name", required = false, defaultValue = "World") String name ,
                                 Model model) throws IOException {
        String login = helper.getLogin();
        String password = helper.getPassword();
        System.out.println(login + "   " + password);
        FollowersDownLoader fd = new FollowersDownLoader();
        ArrayList<String> subscribers = fd.getFollowers(login,password);
        ArrayList<Media> result = new ArrayList<>();
        for(String nick: subscribers){

            ArrayList<Media> path = TestInstagram.getPath(nick);
            if(path == null || path.size() == 0) continue;
            result.addAll(path);
        }

        model.addAttribute("name", name);
        model.addAttribute("omg", result);

        model.addAttribute("helper", helper);
        return "start";
    }

}