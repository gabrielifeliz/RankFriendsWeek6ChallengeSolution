package com.example.demo.controller;

import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.AppRole;
import com.example.demo.model.AppUser;
import com.example.demo.model.Friend;
import com.example.demo.configuration.CloudinaryConfig;
import com.example.demo.service.UserService;
import com.example.demo.repository.AppRoleRepository;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    FriendService friends;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    AppRoleRepository roles;

    @Autowired
    AppUserRepository users;

    @RequestMapping("/")
    public String displayHome(Model model, Authentication authentication) {

        model.addAttribute("friends", friends.rankMyFriends(authentication));

        if (authentication.getAuthorities().contains(roles.findByRole("ADMIN")))
            return "redirect:/friends/ranked";
        else
            return "index";
    }

    @GetMapping("/newfriend")
    public String addFriend(Model model) {
        model.addAttribute("friend", new Friend());
        return "newfriend";
    }

    @PostMapping("/process")
    public String processForm(@Valid @ModelAttribute("friend") Friend friend,
                              BindingResult result, Authentication getDetails,
                              @RequestParam("file")MultipartFile file) {
/*        if (result.hasErrors()) {
            return "newfriend";
        }*/

        if (file != null && !file.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                friend.setImage(uploadResult.get("url").toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            friend.setImage("/img/friends.jpg");
        }
        friends.save(friend, getDetails);
        return "redirect:/friends/";
    }

    @RequestMapping("/ranked")
    public String showRankedFriends(Model model, Authentication authentication) {
        model.addAttribute("friends", friends.rankMyFriends(authentication));
        return "index";
    }
/*
    @RequestMapping("/like/{id}")
    public String likeCounter(@PathVariable("id") long id){
        Friend friend =  friends.findById(id).get();
        if (friend.getRating() < 10) {
            friend.setRating(friend.getRating() + 1);
            friends.save(friend);
        }
        return "redirect:/";
    }

    @RequestMapping("/dislike/{id}")
    public String dislikeCounter(@PathVariable("id") long id){
        Friend friend =  friends.findById(id).get();
        if (friend.getRating() > 1) {
            friend.setRating(friend.getRating() - 1);
            friends.save(friend);
        }
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateFriend( @PathVariable("id") long id, Model model){
        model.addAttribute("friend", friends.findById(id).get());
        return "newfriend";
    }

    @RequestMapping("/delete/{id}")
    public  String deleteFriend(@PathVariable("id") long id){
        friends.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search", searchString);
        model.addAttribute("friends",
                friends.findAllByNameContainingIgnoreCase(searchString));
        return "index";
    }*/

    @PostConstruct
    public void loadData(){

        AppRole admin = new AppRole("ADMIN");
        roles.save(admin);

        AppUser studentLogin = new AppUser("admin", "admin");
        studentLogin.addRole(admin);
        users.save(studentLogin);

    }
}