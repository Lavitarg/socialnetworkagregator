package com.stackabuse.facebookapi.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public MainController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "feed", method = RequestMethod.GET)  //my posts
    public String feed(Model model) {

        if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        PagedList<Post> userFeed = facebook.feedOperations().getFeed();
        model.addAttribute("userFeed", userFeed);
        return "feed";
    }


    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public String groups(Model model) {

        if(connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        List<Group> groups = facebook.fetchConnections("me", "groups", Group.class);
        model.addAttribute("groups", groups);
        /*for (Group group : groups) {
            List<GroupMemberReference> members = facebook.groupOperations().getMembers(group.getName());
        }*/

        return "groups";
    }
}
