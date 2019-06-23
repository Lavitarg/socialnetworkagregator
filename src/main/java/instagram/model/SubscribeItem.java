package instagram.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "subscribers")
public class SubscribeItem {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private InstaUserFilters profile;

    public SubscribeItem() {
    }

    public SubscribeItem(String name, InstaUserFilters profile) {
        this.name = name;
        this.profile = profile;
    }
}
