package instagram.service;

import instagram.model.InstagramSubscription;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import java.io.IOException;
import java.util.List;

public interface InstagramFollowingsWorker {
    public List<String> getFollowingsNames(String name, String password) throws IOException;
    public List<InstagramUserSummary> getFollowings(String name, String password) throws IOException;
    public List<InstagramSubscription> getListOfSubscriptions(String name, String password) throws IOException;

}
