package instagram.service;

import instagram.entity.InstagramProfile;
import instagram.model.InstagramPost;
import me.postaddict.instagram.scraper.model.Media;

import java.io.IOException;
import java.util.List;

public interface PostService {
    public InstagramProfile getInstagramProfile(Long id);
    public List<Media> getMediaByUserId(Long id) throws IOException;
    public List<InstagramPost> getPostByUserId(Long id) throws IOException;
}
