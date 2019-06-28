package instagram.repository;

import instagram.model.VkProfiledUserActor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<VkProfiledUserActor, Integer>{
    Optional<VkProfiledUserActor> findById(Integer userId);
    VkProfiledUserActor findByName(String name);
}
