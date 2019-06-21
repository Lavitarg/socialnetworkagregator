package instagram.service;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FollowersDownLoader  {
    public FollowersDownLoader() {

    }
    public ArrayList<String> getFollowers(String name, String password) throws IOException {
        Instagram4j instagram = Instagram4j.builder().username(name).password(password).build();
        instagram.setup();
        instagram.login();

        InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));

        InstagramGetUserFollowersResult userFollowers = instagram.sendRequest(new InstagramGetUserFollowingRequest(userResult.getUser().getPk()));

        List<InstagramUserSummary> users = userFollowers.getUsers();
        ArrayList<String> names  = users.stream().map(user -> user.getUsername()).collect(Collectors.toCollection(ArrayList<String>::new));
        System.out.println(names.size());
        return names;
    }
}
