package instagram.controller;

import instagram.service.CurrentTimeBeforeGetter;
import instagram.service.FollowersDownLoader;
import instagram.service.Helper_pas;
import instagram.service.TestInstagram;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import instagram.service.Foo;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class GreetingController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greeting(Model model) throws IOException {
        model.addAttribute("helper", new Helper_pas());
        return "greeting";
    }

    public static long currentTime;

    @RequestMapping(value = "/greeting", method = RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Helper_pas helper,
                                 Model model) throws IOException {
        String login = helper.getLogin();
        String password = helper.getPassword();
        System.out.println(login + "   " + password);
        FollowersDownLoader fd = new FollowersDownLoader();
        ArrayList<String> subscribers = fd.getFollowers(login, password);
        model.addAttribute("foo", new Foo());
        model.addAttribute("subs", subscribers);
        return "check";
    }

    @RequestMapping(value = "/processForm", method = RequestMethod.POST)
    public String processForm(@ModelAttribute(value = "foo") Foo foo, Model model) throws IOException {
        // Get value of checked item.

        List<String> checkedItems = foo.getCheckedItems();
        ArrayList<Media> result = new ArrayList<>();
        currentTime = CurrentTimeBeforeGetter.getDate();
        for (String nick : checkedItems) {
            ArrayList<Media> path = TestInstagram.getPath(nick);
            result.addAll(path);
        }
/*for(int i=0; i < 10;i++){
ArrayList<Media> path = TestInstagram.getPath(subscribers.get(i));
result.addAll(path);
}*/
/*ArrayList<Media> path = TestInstagram.getPath("nopbodu");
result.addAll(path);*/
        model.addAttribute("omg", result);
        return "start";

    }

}