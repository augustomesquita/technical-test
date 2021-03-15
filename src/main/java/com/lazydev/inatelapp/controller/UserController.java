package com.lazydev.inatelapp.controller;

import com.lazydev.inatelapp.dto.UserRequest;
import com.lazydev.inatelapp.model.User;
import com.lazydev.inatelapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Getting all users.");
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        log.info(String.format("Getting user with id [%d].", id));
        return userService.getUser(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Creating a new user.");
        return userService.saveUser(User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build());
    }

    @PutMapping("/users/{id}")
    public User updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable Long id) {
        log.info(String.format("Updating user with id [%d].", id));
        User user = userService.getUser(id);
        return userService.saveUser(User.builder()
                .id(user.getId())
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build());
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info(String.format("Deleting user with id [%d].", id));
        User user = userService.getUser(id);
        userService.deleteUser(user);
    }
}
