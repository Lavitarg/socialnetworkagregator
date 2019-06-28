package instagram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class VkPost extends Post {

    private String text;

    private List<String> media;

    private Date date;
    public VkPost(String text, List<String> media, Date date) {
        this.text = text;
        this.media = media;
        this.date = date;
    }
}
