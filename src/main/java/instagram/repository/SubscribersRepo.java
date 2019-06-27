package instagram.repository;

import instagram.model.SubscribeItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscribersRepo  extends CrudRepository<SubscribeItem, Long> {
    List<SubscribeItem> findAllByProfileId(Long id);
    SubscribeItem findByName(String name);
}
