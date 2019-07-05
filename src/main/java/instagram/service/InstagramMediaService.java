package instagram.service;

import me.postaddict.instagram.scraper.model.Media;

import java.io.IOException;
import java.util.List;

public interface InstagramMediaService {
    public List<Media> getMedia(String name) throws IOException;
    public long getDate();
}
