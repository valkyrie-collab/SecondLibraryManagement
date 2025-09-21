package com.valkyrie.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valkyrie.authentication.model.User;
import com.valkyrie.authentication.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user) {return userService.signUp(user);}

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody User user) {return userService.signIn(user);}

    @GetMapping("/get-user")
    public ResponseEntity<User> getUser(@RequestBody String username) {return userService.getUser(username);}
}
