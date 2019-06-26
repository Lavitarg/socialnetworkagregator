package agregator.vk.model;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Entity;

@AllArgsConstructor
@Entity
public class ProfiledUserActor extends UserActor {

    private String name;

    @Builder
    public ProfiledUserActor(Integer userId, String accessToken, String firstname, String lastname) {
        super(userId, accessToken);
        this.name = name;
    }


}
