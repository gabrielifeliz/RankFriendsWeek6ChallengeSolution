package com.example.demo.controller;


import com.example.demo.model.AppUser;
import com.example.demo.configuration.CloudinaryConfig;
import com.example.demo.service.UserService;
import com.example.demo.repository.AppRoleRepository;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    FriendService friends;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    AppRoleRepository roles;

    @Autowired
    AppUserRepository users;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String listOfFriends() {
        return "redirect:/friends/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new AppUser());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") AppUser user,
            BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "login";
    }
}