package instagram.model;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Post {
    private Date timestamp;
    private UserModel owner;

    public Post() {
    }
}
