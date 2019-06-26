package instagram.repository;

import instagram.entity.InstagramProfile;
import org.springframework.data.repository.CrudRepository;

public interface InstagramProfileRepo extends CrudRepository<InstagramProfile, Long> {
    InstagramProfile findByLogin(String login);
    InstagramProfile findByUserId(Long Id);
}