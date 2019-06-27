package instagram.service;

import java.io.IOException;
import java.util.List;

public interface InstagramFollowingsWorker {
    public List<String> getFollowers(String name,String password) throws IOException;
}
