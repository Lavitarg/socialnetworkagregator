package instagram.model;
import lombok.AllArgsConstructor;
import javax.persistence.Entity;

@AllArgsConstructor
@Entity
public class VKSubscription {
    private Integer id;
    private VKSubscriptionType type;
    private String name;
}
