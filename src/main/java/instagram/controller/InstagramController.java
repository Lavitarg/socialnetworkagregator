package instagram.controller;

import instagram.entity.InstagramProfile;
import instagram.entity.User;

import instagram.model.*;

import instagram.service.RepositoryWorker;
import lombok.RequiredArgsConstructor;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class InstagramController {
    private final RepositoryWorker repositoryWorker;

    @GetMapping(value = "/filter")
    public String greeting(@AuthenticationPrincipal User user, Model model) throws IOException {
        String name = repositoryWorker.getInstagramProfileNameByOwnerId(user.getId());
        model.addAttribute("nickname", name);
        model.addAttribute("helper", new SubsChangeObject());
        return "filter";
    }


    @PostMapping(value = "/filter")
    public String greetingSubmit(@AuthenticationPrincipal User user,
                                 @ModelAttribute SubsChangeObject helper, Model model) throws IOException {
        List<String> subscribers = repositoryWorker.getFollowers(helper.getLogin(), helper.getPassword());
        model.addAttribute("filter", new SubsFilter());
        model.addAttribute("subs", subscribers);
        return "check";
    }

    @PostMapping(value = "/processForm")
    public String processForm(@AuthenticationPrincipal User user,
                              @ModelAttribute(value = "foo") SubsFilter filter, Model model) throws IOException {
        InstagramProfile profile = repositoryWorker.getInstagramProfile(user.getId());
        model.addAttribute("login", profile.getLogin());
        repositoryWorker.saveSubscribers(filter.getCheckedItems(), profile);
        return "redirect:/instagram";
    }

    @GetMapping(value = "/tape")
    public String tape(@AuthenticationPrincipal User user, Model model) throws IOException {
        boolean exist = repositoryWorker.checkIfInstagramProfileExistsByOwnerId(user.getId());
        if (!exist) {
            return "redirect:/newInstagramProfile";
        }
        List<Media> result = repositoryWorker.getMediaByUserId(user.getId());
        model.addAttribute("mediaList", result);
        return "tape";
    }

    @GetMapping(value = "/newInstagramProfile")
    public String newProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("helper", new NameChanger());
        model.addAttribute("text", "Add new Profile");
        return "loginChanger";
    }

    @PostMapping(value = "/newInstagramProfile")
    public String newProfileSaver(@AuthenticationPrincipal User user, @ModelAttribute NameChanger helper, Model model) {
        repositoryWorker.saveInstagramProfile(helper.getName(), user);
        return "redirect:/instagram";
    }

    @GetMapping(value = "/instagram")
    public String instagramMenu(@AuthenticationPrincipal User user, Model model) {
        boolean exist = repositoryWorker.checkIfInstagramProfileExistsByOwnerId(user.getId());
        if (!exist) {
            return "redirect:/newInstagramProfile";
        }
        model.addAttribute("name", repositoryWorker.getInstagramProfileNameByOwnerId(user.getId()));
        return "instagramMenu";
    }

    @GetMapping(value = "/changeNick")
    public String changeNickName(@AuthenticationPrincipal User user, Model model) {
        boolean exist = repositoryWorker.checkIfInstagramProfileExistsByOwnerId(user.getId());
        if (!exist) {
            return "redirect:/newInstagramProfile";
        }
        model.addAttribute("helper", new NameChanger());
        model.addAttribute("text", "Change login");
        return "loginChanger";
    }

    // We have some bad solution is here((((((( doesn't work
    @PostMapping(value = "/changeNick")
    public String changeNickName(@AuthenticationPrincipal User user, @ModelAttribute NameChanger changer, Model model) {
        repositoryWorker.changeProfileLoginByOwnerId(user.getId(), changer.getName());
        return "redirect:/instagram";
    }
}
