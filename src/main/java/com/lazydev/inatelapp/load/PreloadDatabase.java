package com.lazydev.inatelapp.load;

import com.lazydev.inatelapp.service.UserService;
import com.lazydev.inatelapp.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PreloadDatabase {

    @Bean
    CommandLineRunner preload(UserService userService) {
        return args -> {
            log.info("Preloading " + userService.saveUser(new User("Augusto Mesquita", "augustomesquita@email.com")));
        };
    }
}
