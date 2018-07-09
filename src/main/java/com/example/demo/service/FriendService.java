package com.example.demo.service;

import com.example.demo.model.AppUser;
import com.example.demo.model.Friend;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

    private AppUser user;

    @Autowired
    AppUserRepository users;

    @Autowired
    FriendRepository friends;

    public Iterable<Friend> getMyFriends(Authentication myDetails) {
        return friends.findAllByMyFriendOrderByRatingDesc(users.findByUsername(myDetails.getName()));
    }

    public Iterable<Friend> rankMyFriends(Authentication myDetails) {
        return friends.findAllByMyFriendOrderByRatingDesc(users.findByUsername(myDetails.getName()));
    }

    public Friend save(Friend friend, Authentication authentication) {
        user = users.findByUsername(authentication.getName());
        friend.setMyFriend(user);
        return friends.save(friend);
    }
}
