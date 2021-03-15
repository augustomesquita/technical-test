package com.lazydev.inatelapp;

import com.lazydev.inatelapp.model.User;
import com.lazydev.inatelapp.repository.UserRepository;
import com.lazydev.inatelapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class SpringApplicationTests {

    @InjectMocks
    UserService userService;

    @Mock
    Pageable pageableMock;

    @Mock
    UserRepository userRepository;

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void getAllUsersPageable() {
        // Given
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "test1", "test1@email.com"));
        users.add(new User(2L, "test2", "test2@email.com"));
        users.add(new User(3L, "test3", "test3@email.com"));
        Page<User> usersPageable = new PageImpl(users);

        // When
        when(userRepository.findAll(pageableMock)).thenReturn(usersPageable);
        Page<User> allUsers = userService.getUsersPageable(pageableMock);

        // Then
        assertEquals(3, allUsers.getTotalElements());
        verify(userRepository, times(1)).findAll(pageableMock);
    }

}

