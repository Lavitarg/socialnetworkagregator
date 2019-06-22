package instagram.dao;

import instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
