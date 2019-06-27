package vk.repository;

import vk.model.ProfiledUserActor;
import org.springframework.data.jpa.repository.JpaRepository;
import vk.model.ProfiledUserActor;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<ProfiledUserActor, Integer>{
    Optional<ProfiledUserActor> findById(Integer userId);
    ProfiledUserActor findByName(String name);
}
