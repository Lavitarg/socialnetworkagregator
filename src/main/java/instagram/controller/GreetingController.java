package instagram.controller;

import instagram.service.TestInstagram;
import me.postaddict.instagram.scraper.model.Comment;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;


@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) throws IOException {
        model.addAttribute("name", name);
        ArrayList<Media> path = TestInstagram.getPath();
        model.addAttribute("omg",path);
        return "greeting";
    }

}