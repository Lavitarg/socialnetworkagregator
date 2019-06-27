package instagram.service.impl;

import instagram.Exeptions.UnableToAuthorizeException;
import instagram.repository.VKUsersRepository;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.account.UserSettings;
import instagram.service.VKAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;

public class VKAuthorizationServiceImpl implements VKAuthorizationService {
    private final Integer appId = 7033540;

    private final String key = "VoDvwVOcnzTmuYgsi7FG";

    private final String redirectUri = "http://localhost:8080/getcode";

    @Autowired
    private VKUsersRepository repository;

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
        try {
            UserSettings infoResponse = vk.account()
                    .getProfileInfo(new UserActor(authResponse.getUserId(), authResponse.getAccessToken()))
                    .execute();
            /*VKProfiledUserActor user = VKProfiledUserActor.builder()
                    .build()
                    .userId(authResponse.getUserId())
                    .accessToken(authResponse.getAccessToken())
                    .name(infoResponse.getFirstName() + " " + infoResponse.getLastName());
            repository.save(user);*/
        } catch (ApiException apiException) {
            throw new UnableToAuthorizeException("Cannot get user's name.", apiException);
        } catch (ClientException clientException) {
            throw new UnableToAuthorizeException("Cannot get user's name.", clientException);
        }
    }
}
