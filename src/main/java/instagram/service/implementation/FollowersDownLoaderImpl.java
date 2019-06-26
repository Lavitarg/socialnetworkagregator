package instagram.service.implementation;

import instagram.service.InstagramFollowersWorker;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class FollowersDownLoaderImpl implements InstagramFollowersWorker {
    @Override
    public List<String> getFollowers(String name, String password) throws IOException {
        Instagram4j instagram = Instagram4j.builder().username(name).password(password).build();
        instagram.setup();
        instagram.login();

        InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));

        InstagramGetUserFollowersResult userFollowers = instagram.sendRequest(new InstagramGetUserFollowingRequest(userResult.getUser().getPk()));

        List<InstagramUserSummary> users = userFollowers.getUsers();
        List<String> names  = users.stream()
                .map(user -> user.getUsername())
                .collect(Collectors.toList());
        return names;
    }
}
