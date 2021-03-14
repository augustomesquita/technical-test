package com.lazydev.inatelapp.controller;

import com.lazydev.inatelapp.dto.UserRequest;
import com.lazydev.inatelapp.model.User;
import com.lazydev.inatelapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/pageable")
    public Page<User> getUsersPageable() {
        return userService.getUsersPageable();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@Valid @RequestBody UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable Long id) {
        User user = userService.getUser(id);
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        return userService.saveUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        userService.deleteUser(user);
    }
}
