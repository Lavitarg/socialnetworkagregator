package instagram.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriber")
public class Subscriber {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private InstagramProfile profile;

    public Subscriber(String name, InstagramProfile profile) {
        this.name = name;
        this.profile = profile;
    }
}
