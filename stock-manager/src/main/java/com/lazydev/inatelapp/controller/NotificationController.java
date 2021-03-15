package com.lazydev.inatelapp.controller;

import com.lazydev.inatelapp.model.ClientAddress;
import com.lazydev.inatelapp.model.Stock;
import com.lazydev.inatelapp.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification")
    public List<ClientAddress> getClient() {
        return notificationService.getClientAddresses();
    }

    @PostMapping("/notification")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientAddress addClient(@Valid @RequestBody ClientAddress clientAddress) {
        log.info("Add client address...");
        clientAddress = notificationService.addClient(clientAddress);
        log.info("Client address was added successfully.");
        return clientAddress;
    }


}
