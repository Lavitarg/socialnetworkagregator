package agregator.vk.repository;

import agregator.vk.model.ProfiledUserActor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<ProfiledUserActor, Integer>{
    ProfiledUserActor findById(Integer userId);
    ProfiledUserActor findByName(String name);
}
