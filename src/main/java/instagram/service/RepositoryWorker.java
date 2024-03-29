package instagram.service;

import instagram.entity.InstagramProfile;
import instagram.entity.Role;
import instagram.entity.Subscriber;
import instagram.entity.User;
import instagram.repository.InstagramProfileRepo;
import instagram.repository.SubscriprionsRepo;
import instagram.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepositoryWorker {
    private final UserRepo userRepo;
    private final InstagramProfileRepo profileRepo;
    private final SubscriprionsRepo subscribersRepo;
    private final InstagramFollowingsWorker followersWorker;
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

    public void saveSubscriptions(List<String> subscribers, InstagramProfile instagramProfile) {
        for (String nick : subscribers) {
            if (subscribersRepo.findByName(nick) == null) {
                Subscriber item = new Subscriber(nick, instagramProfile);
                subscribersRepo.save(item);
            }
        }
    }

    public List<String> getFollowing(String login, String password) throws IOException {
        List<String> subscribers = followersWorker.getFollowingsNames(login, password);
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

    public List<String> getCurrentSubscriptions(long id) {
        InstagramProfile profile = this.getInstagramProfile(id);
        List<Subscriber> subscribeItems = subscribersRepo.findAllByProfileId(profile.getId());
        return subscribeItems
                .stream()
                .map(subscriber -> subscriber.getName())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSubscriptions(List<String> checkedItems, long id) {
        InstagramProfile profile = this.getInstagramProfile(id);
        for(String name: checkedItems){
            subscribersRepo.deleteByProfileIdAndName(profile.getId(),name);
        }
    }
}
