package instagram.controller;

import instagram.entity.User;
import instagram.model.InstagramSubscription;
import instagram.model.SubsChangeObject;
import instagram.model.SubsFilter;
import instagram.service.InstagramFollowingsWorker;
import instagram.service.RepositoryWorker;
import lombok.RequiredArgsConstructor;
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

public class SubscriptionController {
    private final RepositoryWorker repositoryWorker;
    private final InstagramFollowingsWorker followingsWorker;
    //Меппинг для считывание логина и пароля, чтобы достать список подписок
    @GetMapping(value = "/filt")
    public String filt(@AuthenticationPrincipal User user, Model model) throws IOException {
        String name = repositoryWorker.getInstagramProfileName(user.getId());
        model.addAttribute("nickname", name);
        model.addAttribute("helper", new SubsChangeObject());
        return "testsheet";
    }

    //Меппинг достаёт подписки из инстаграма для добавления их в форму с чекбоксами
    @PostMapping(value = "/filt")
    public String filtSubmit(@AuthenticationPrincipal User user,
                               @ModelAttribute SubsChangeObject helper, Model model) throws IOException {
        List<InstagramSubscription> subscribers = followingsWorker.getListOfSubscriptions(helper.getLogin(), helper.getPassword());
        model.addAttribute("filter", new SubsFilter());
        model.addAttribute("subs", subscribers);
        return "instagramSubscription";
    }

}
