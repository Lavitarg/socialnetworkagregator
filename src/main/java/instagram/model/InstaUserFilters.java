package instagram.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "prf")
public class InstaUserFilters {
    @Id
    @GeneratedValue
    private long id;

    private String login;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public InstaUserFilters() {
    }

    public InstaUserFilters(String login, User user) {
        this.login = login;
        this.user = user;
    }
}
