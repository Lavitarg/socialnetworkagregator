package instagram.service;

import instagram.entity.InstagramProfile;
import instagram.entity.Role;
import instagram.entity.Subscriber;
import instagram.entity.User;
import instagram.repository.InstagramProfileRepo;
import instagram.repository.SubscribersRepo;
import instagram.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryWorker {
    private final UserRepo userRepo;
    private final InstagramProfileRepo profileRepo;
    private final SubscribersRepo subscribersRepo;
    private final InstagramFollowersWorker followersWorker;
    private final InstagramMediaWorker instagramMediaWorker;

    public boolean checkIfUserExist(String name) {
        User userByName = userRepo.findByUsername(name);
        return (userByName != null);
    }

    public void saveUser(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
    }

    public String getInstagramProfileNameByOwnerId(Long id) {
        InstagramProfile instagramProfile = this.getInstagramProfile(id);
        return instagramProfile.getLogin();
    }

    public boolean checkIfInstagramProfileExistsByOwnerId(Long id) {
        InstagramProfile instagramProfile = this.getInstagramProfile(id);
        return (instagramProfile != null);
    }

    public InstagramProfile getInstagramProfile(Long id) {
        return profileRepo.findByUserId(id);
    }

    public void saveInstagramProfile(String name, User user) {
        InstagramProfile instaUser = new InstagramProfile(name, user);
        profileRepo.save(instaUser);
    }

    public void saveSubscribers(List<String> subscribers, InstagramProfile instagramProfile) {
        for (String nick : subscribers) {
            if (subscribersRepo.findByName(nick) == null) {
                Subscriber item = new Subscriber(nick, instagramProfile);
                subscribersRepo.save(item);
            }
        }
    }

    public List<String> getFollowers(String login, String password) throws IOException {
        List<String> subscribers = followersWorker.getFollowers(login, password);
        return subscribers;
    }

    public List<Media> getMediaByUserId(Long id) throws IOException {
        InstagramProfile profile = this.getInstagramProfile(id);
        List<Subscriber> subscribeItems = subscribersRepo.findAllByProfileId(profile.getId());
        List<Media> result = new ArrayList<>();
        for (Subscriber nick : subscribeItems) {
            List<Media> mediaList = instagramMediaWorker.getMedia(nick.getName());
            result.addAll(mediaList);
        }
        return result;
    }

    public void changeProfileLoginByOwnerId(Long id,String login) {
        InstagramProfile profile = this.getInstagramProfile(id);
        profile.setLogin(login);
        profileRepo.save(profile);
    }
}
