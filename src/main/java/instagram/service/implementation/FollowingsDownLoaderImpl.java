package instagram.service.implementation;

import instagram.model.InstagramSubscription;
import instagram.service.InstagramFollowingsWorker;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class FollowingsDownLoaderImpl implements InstagramFollowingsWorker {
    @Override
    public List<String> getFollowingsNames(String name, String password) throws IOException {

        List<InstagramUserSummary> users = this.getFollowings(name,password);
        List<String> names  = users.stream()
                .map(user -> user.getUsername())
                .collect(Collectors.toList());
        return names;
    }

    @Override
    public List<InstagramUserSummary> getFollowings(String name, String password) throws IOException {
        Instagram4j instagram = Instagram4j.builder().username(name).password(password).build();
        instagram.setup();
        instagram.login();

        InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));

        InstagramGetUserFollowersResult userFollowers = instagram.sendRequest(new InstagramGetUserFollowingRequest(userResult.getUser().getPk()));
        List<InstagramUserSummary> users = userFollowers.getUsers();
        return users;
    }

    @Override
    public List<InstagramSubscription> getListOfSubscriptions(String name, String password) throws IOException {
        List<InstagramUserSummary> users = this.getFollowings(name,password);
        return users.stream().map(instagramUserSummary -> new InstagramSubscription(instagramUserSummary)).collect(Collectors.toList());
    }

}
