package instagram.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instagram_profile")
public class InstagramProfile {
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String login;

    @NonNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public InstagramProfile(String login, User user) {
        this.login = login;
        this.user = user;
    }
}
