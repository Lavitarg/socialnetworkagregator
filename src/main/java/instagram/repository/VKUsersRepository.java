package instagram.repository;

import instagram.model.VKProfiledUserActor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface VKUsersRepository extends JpaRepository<VKProfiledUserActor, Integer>{
    Optional<VKProfiledUserActor> findById(Integer userId);
    VKProfiledUserActor findByName(String name);
}
