package instagram.service.implementation;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.newsfeed.NewsfeedItem;
import com.vk.api.sdk.objects.newsfeed.NewsfeedItemType;
import instagram.model.VkPost;
import instagram.model.VkProfiledUserActor;
import instagram.repository.VkUserRepository;
import instagram.service.VkNewsFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class VkNewsFeedServiceImpl implements VkNewsFeedService {
    @Autowired
    private VkUserRepository repo;

    private VkProfiledUserActor userActor;

    private TransportClient transportClient;

    private VkApiClient vk;

    private List<VkPost> news;

    private void get(){
        try {
            // RETRIEVING DATA FROM VK JSON SCHEMA
            Gson gson = new Gson();
            Map<String, Object> newsMap;
            List<NewsfeedItem> newsList = vk.newsfeed().get(userActor).execute().getItems();
            news = new ArrayList<>();
            List<String> attachments = new ArrayList<>();
            for (NewsfeedItem item : newsList){
                newsMap = gson.fromJson(item.toString(), Map.class);
                List <Map<String, String>> attached = (List<Map<String, String>>) newsMap.get("attachments");
                for (Map attachment : attached) {
                    Map<String, String> container = (Map<String, String>) attachment.get(attachment.get("type"));
                    if (attachment.get("type").equals("photo")) {
                        attachments.add(container.get("photo_807"));
                    } else {
                        attachments.add(container.get(attachment.get("type")));
                    }
                }
                String text = "Media.";
                if (item.getType() == NewsfeedItemType.POST) {
                    text = (String) newsMap.get("text");
                }
                news.add(new VkPost(text, attachments, new Date(Long.valueOf((String) newsMap.get("date")))));
            }
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

    private void init(){
       transportClient = HttpTransportClient.getInstance();
       vk = new VkApiClient(transportClient);
    }

    @Override
    public List<VkPost> getNews(String name) {
        init();
        userActor = repo.findByName(name);
        get();
        return this.news;
    }

    @Override
    public List<VkPost> getNews(Integer userId) {
        init();
        userActor = repo.findById(userId).get();
        get();
        return this.news;
    }
}
