package instagram.model;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "actor")
public class VkProfiledUserActor extends UserActor {
    @Id
    @GeneratedValue
    private Integer id;


    private String name;

    @Builder
    public VkProfiledUserActor(Integer userId, String accessToken, String firstname, String lastname) {
        super(userId, accessToken);
        this.name = name;
    }


}
