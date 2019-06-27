package agregator.vk.model;
import lombok.AllArgsConstructor;
import javax.persistence.Entity;

@AllArgsConstructor
@Entity
public class Subscription {
    private Integer id;
    private SubscriptionType type;
    private String name;
}
