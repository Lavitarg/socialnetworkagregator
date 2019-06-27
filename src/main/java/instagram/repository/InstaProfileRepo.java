package instagram.repository;

import instagram.model.InstaUserFilters;
import org.springframework.data.repository.CrudRepository;

public interface InstaProfileRepo extends CrudRepository<InstaUserFilters, Long> {
    InstaUserFilters findByLogin(String login);
    InstaUserFilters findByUserId(Long Id);
}