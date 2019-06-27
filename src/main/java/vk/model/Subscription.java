package vk.model;
import lombok.AllArgsConstructor;
import javax.persistence.Entity;

@AllArgsConstructor
@Entity
public class Subscription {
    private Integer id;
    private vk.model.SubscriptionType type;
    private String name;
}
