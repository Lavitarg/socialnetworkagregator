    package instagram.controller;

    import instagram.entity.InstagramProfile;
    import instagram.entity.User;

    import instagram.model.*;

    import instagram.service.InstagramFollowingsWorker;
    import instagram.service.PostService;
    import instagram.service.RepositoryWorker;
    import lombok.RequiredArgsConstructor;
    import me.postaddict.instagram.scraper.model.Media;
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
        private final PostService postService;
        private final InstagramFollowingsWorker followingsWorker;
        //Меппинг для удаления существующих подписок в фильтрации
        @GetMapping(value = "/oldSubs")
        public String prefilter(@AuthenticationPrincipal User user, Model model) throws IOException {
            List<String> subscribers = repositoryWorker.getCurrentSubscriptions(user.getId());
            model.addAttribute("filter", new SubsFilter());
            model.addAttribute("subs", subscribers);
            return "changeCurrentFilter";
        }

        //Обработка формы с удаляемыми из фильтрации подписками
        @PostMapping(value = "/oldSubs")
        public String preFilterSubmit(@AuthenticationPrincipal User user,
                                   @ModelAttribute SubsFilter filter, Model model) throws IOException {
            repositoryWorker.deleteSubscriptions(filter.getCheckedItems(),user.getId());
            return "redirect:/instagram";
        }

        //Меппинг для считывание логина и пароля, чтобы достать список подписок
        @GetMapping(value = "/filter")
        public String filter(@AuthenticationPrincipal User user, Model model) throws IOException {
            String name = repositoryWorker.getInstagramProfileName(user.getId());
            model.addAttribute("nickname", name);
            model.addAttribute("helper", new SubsChangeObject());
            return "filter";
        }


        //Меппинг достаёт подписки из инстаграма для добавления их в форму с чекбоксами
        @PostMapping(value = "/filter")
        public String filterSubmit(@AuthenticationPrincipal User user,
                                   @ModelAttribute SubsChangeObject helper, Model model) throws IOException {
            try {
                List<String> subscribers = repositoryWorker.getFollowing(helper.getLogin(), helper.getPassword());
                model.addAttribute("filter", new SubsFilter());
                model.addAttribute("subs", subscribers);
            }
            catch (NullPointerException e){
                return "errorPage";
            }
            return "check";
        }

        //Меппинг сохраняет выбранные подписки в текущую фильтрацию
        @PostMapping(value = "/processForm")
        public String processForm(@AuthenticationPrincipal User user,
                                  @ModelAttribute(value = "foo") SubsFilter filter, Model model) throws IOException {
            InstagramProfile profile = repositoryWorker.getInstagramProfile(user.getId());
            model.addAttribute("login", profile.getLogin());
            repositoryWorker.saveSubscriptions(filter.getCheckedItems(), profile);
            return "redirect:/instagram";
        }

        //Меппинг для генерации ленты с помощью id авторизованного пользователя
        @GetMapping(value = "/feed")
        public String tape(@AuthenticationPrincipal User user, Model model) throws IOException {
            boolean exist = repositoryWorker.checkIfInstagramProfileExists(user.getId());
            if (!exist) {
                return "redirect:/newInstagramProfile";
            }
            List<Media> result = postService.getMediaByUserId(user.getId());
            model.addAttribute("mediaList", result);
            model.addAttribute("login", postService.getInstagramProfile(user.getId()).getLogin());
            return "tape";
        }

        //Меппинг для страницы добавления нового профиля instagram
        //TODO это же не новый профиль, это же просто отображаемое имя? Убрать вообще этот мапинг и ссылки на него, отображать логин
        @GetMapping(value = "/newInstagramProfile")
        public String newProfile(@AuthenticationPrincipal User user, Model model) {
            model.addAttribute("helper", new NameChanger());
            model.addAttribute("text", "Add new Profile");
            return "addAccount";
        }

        //Мепинг для сохранения добавленного пользователя
        @PostMapping(value = "/newInstagramProfile")
        public String newProfileSaver(@AuthenticationPrincipal User user, @ModelAttribute NameChanger helper, Model model) {
            repositoryWorker.saveInstagramProfile(helper.getName(), user);
            return "redirect:/instagram";
        }

        //Меппинг для просмотра доступного функционала instagram модуля
        @GetMapping(value = "/instagram")
        public String instagramMenu(@AuthenticationPrincipal User user, Model model) {
            boolean exist = repositoryWorker.checkIfInstagramProfileExists(user.getId());
            if (!exist) {
                return "redirect:/newInstagramProfile";
            }
            model.addAttribute("name", repositoryWorker.getInstagramProfileName(user.getId()));
            return "instagramMenu";
        }

        //Меппинг для смены названия аккаунтаю
        @GetMapping(value = "/changeNick")
        public String changeNickName(@AuthenticationPrincipal User user, Model model) {
            boolean exist = repositoryWorker.checkIfInstagramProfileExists(user.getId());
            if (!exist) {
                return "redirect:/newInstagramProfile";
            }
            model.addAttribute("helper", new NameChanger());
            model.addAttribute("text", "Change login");
            return "changeInstagramLogin";
        }

        /*//Быстрый тест сервиса для генерации постов
        @GetMapping(value = "/checkIfPostServiceWorks")
        public String check(@AuthenticationPrincipal User user, Model model) throws IOException {
            List<InstagramPost> posts = postService.getPostByUserId(user.getId());
            System.out.println();
            return "main";
        }*/

        //Меппинг для сохранения изменения в названии аккаунта
        @PostMapping(value = "/changeNick")
        public String changeNickName(@AuthenticationPrincipal User user, @ModelAttribute NameChanger changer, Model model) {
            repositoryWorker.changeProfileLoginByOwnerId(user.getId(), changer.getName());
            return "redirect:/instagram";
        }


    }
