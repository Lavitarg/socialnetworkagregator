package instagram.service.implementation;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import instagram.model.VkProfiledUserActor;
import instagram.repository.VkUserRepository;
import instagram.exception.UnableToAuthorizeException;
import instagram.service.VkAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkAuthorizationServiceImpl implements VkAuthorizationService {
    private final Integer appId = 7036814;

    private final String key = "MlbwUPOmPcLPtzqruB4h";

    private final String redirectUri = "http://8008264e.ngrok.io/getvkcode";

    @Autowired
    private VkUserRepository repository;

    @Override
    public void authorize(String code) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        UserAuthResponse authResponse = null;
        try {
            authResponse = vk.oauth()
                    .userAuthorizationCodeFlow(appId, key, redirectUri, code)
                    .execute();
        } catch (ApiException apiException) {
            throw new UnableToAuthorizeException("Cannot get access token", apiException);
        } catch (ClientException clientException) {
            throw new UnableToAuthorizeException("Cannot get access token", clientException);
        }
        List<UserXtrCounters> infoResponse = null;
        try {
            infoResponse = vk.users().get(new UserActor(authResponse.getUserId(), authResponse.getAccessToken()))
                    .execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        String name = infoResponse.get(0).getFirstName() + infoResponse.get(0).getLastName();
        System.out.println(name);
        VkProfiledUserActor user = new VkProfiledUserActor(authResponse.getUserId(), authResponse.getAccessToken(), name);
        repository.save(user);
    }
}
