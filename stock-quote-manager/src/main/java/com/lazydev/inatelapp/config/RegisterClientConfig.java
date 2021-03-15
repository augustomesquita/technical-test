package com.lazydev.inatelapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Configuration
public class RegisterClientConfig {

    private static final String HOST = "localhost";
    private static final Integer PORT = 8081;

    @Bean
    CommandLineRunner registerClientOnStockManagerAPI() {
        return args -> {
            try {
                URL url = new URL("http://localhost:8080/api/notification");
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                String jsonInputString = String.format("{\"host\": \"%s\", \"port\": %d}", HOST, PORT);

                try(OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    if (response.toString().contains(HOST)) {
                        log.info("Client has been registered successfully on stock manager API.");
                    } else {
                        log.error("Error. Client has not been registered successfully on stock manager API.");
                    }
                }
            } catch (IOException e) {
                log.error("Error. Client has not been registered successfully on stock manager API. Check if the stock manager API is alive...");
            }

        };
    }
}
