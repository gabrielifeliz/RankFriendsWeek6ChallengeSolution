package com.example.demo.repository;

import com.example.demo.model.AppUser;
import com.example.demo.model.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendRepository extends CrudRepository<Friend, Long> {
    Iterable<Friend> findAllByMyFriend(AppUser appUser);
    Iterable<Friend> findAllByMyFriendOrderByRatingDesc(AppUser appUser);
}
