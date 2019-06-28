package instagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstagramSubscription {
    private String nick;
    private String profileImageurl;

    public InstagramSubscription(InstagramUserSummary instagramUserSummary) {
        this.nick = instagramUserSummary.getUsername();
        this.profileImageurl = instagramUserSummary.getProfile_pic_url();
    }
}
