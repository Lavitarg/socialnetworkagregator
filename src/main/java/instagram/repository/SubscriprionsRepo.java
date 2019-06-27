package instagram.repository;

import instagram.entity.Subscriber;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriprionsRepo extends CrudRepository<Subscriber, Long> {
    List<Subscriber> findAllByProfileId(Long id);
    Subscriber findByName(String name);
    void deleteByProfileIdAndName(Long id,String name);
}
