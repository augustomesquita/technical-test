package com.lazydev.inatelapp.service;

import com.lazydev.inatelapp.model.ClientAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
public class NotificationService {

    CopyOnWriteArraySet<ClientAddress> clientAddresses;

    public NotificationService() {
        this.clientAddresses = new CopyOnWriteArraySet<>();
    }

    public List<ClientAddress> getClientAddresses() {
        return new ArrayList<>(this.clientAddresses);
    }

    public ClientAddress addClient(ClientAddress clientAddress) {
        this.clientAddresses.add(clientAddress);
        return clientAddress;
    }

    public void notifyClients() {
        getClientAddresses().forEach(clientAddress -> {
            try {
                URL url = new URL(String.format("http://%s:%d/api/stockcache", clientAddress.getHost(), clientAddress.getPort()));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.getInputStream();
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        });

    }

}
