package agregator.vk.controller;

import agregator.vk.service.AuthorizationService;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.account.UserSettings;
import com.vk.api.sdk.objects.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizationController {

    private final Integer appId = 7033540;


    private final String key = "VoDvwVOcnzTmuYgsi7FG";

    private final String redirectUri = "http://localhost:8080/getcode";

    @Autowired
    private AuthorizationService service;

    @GetMapping (value = "/authorize")
    public String authorize(){
        return "redirect:https://oauth.vk.com/authorize?client_id=" + appId + "&display=page&" +
                "redirect_uri=" + redirectUri + "&scope=friends,wall,offline &response_type=code&v=5.95";
    }

    @GetMapping (value = "/getcode")
    public String createUserActor(@RequestParam String code) throws ClientException, ApiException {
        service.authorize(code);
        return "redirect:/main";
    }
}
