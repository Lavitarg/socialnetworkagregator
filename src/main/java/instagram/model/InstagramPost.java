package instagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.postaddict.instagram.scraper.model.Media;

import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstagramPost extends Post {
    private String desctiption;
    private long likes;
    private String url;
    private String originalLink;
    public InstagramPost(Media media){
        this.timestamp = media.getCreated();
        this.desctiption = media.getCaption();
        this.likes = media.getLikeCount();
        this.url = media.getDisplayUrl();
        this.originalLink = "https://www.instagram.com/p/" + media.getShortcode();
    }
}
