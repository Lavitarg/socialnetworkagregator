package instagram.service;

import instagram.model.InstagramPost;
import me.postaddict.instagram.scraper.model.Media;

import java.io.IOException;
import java.util.List;

public interface PostService {
    public List<Media> getMediaByUserId(Long id) throws IOException;
    public List<InstagramPost> getPostByUserId(Long id) throws IOException;
}
