package instagram.service;

import instagram.model.VkPost;

import java.util.List;

public interface VkNewsFeedService {
    List<VkPost> getNews(String name);
    List<VkPost> getNews(Integer userId);
}
