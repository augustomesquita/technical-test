package com.lazydev.inatelapp.service;

import com.lazydev.inatelapp.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    Page<User> getUsersPageable();

    User getUser(Long id);

    User saveUser(User user);

    void deleteUser(User user);
}
