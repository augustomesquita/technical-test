package com.lazydev.inatelapp.config;

import com.lazydev.inatelapp.model.User;
import com.lazydev.inatelapp.service.UserService;
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
            final User preloadUser = userService.saveUser(User.builder()
                    .name("Augusto Mesquita")
                    .email("augustomesquita@email.com")
                    .build());
            log.info(String.format("Preloading: [%s]", preloadUser.toString()));
        };
    }
}
