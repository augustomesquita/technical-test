package com.lazydev.inatelapp.controller;

import com.lazydev.inatelapp.dto.StockRequest;
import com.lazydev.inatelapp.model.Stock;
import com.lazydev.inatelapp.service.NotificationService;
import com.lazydev.inatelapp.service.StockService;
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
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/stock")
    public List<String> getStocks() {
        log.info("Getting all stock...");
        List<String> stocks = stockService.getStocks().stream()
                .map(Stock::getId)
                .collect(Collectors.toList());
        log.info("All stock registered were returned successfully.");
        return stocks;
    }

    @PostMapping("/stock")
    @ResponseStatus(HttpStatus.CREATED)
    public Stock saveStock(@Valid @RequestBody StockRequest stockRequest) {
        log.info("Creating stock...");
        Stock stockCreated = stockService.saveStock(stockRequest);
        log.info("Stock quote with id [{}] was created successfully.", stockCreated.getId());
        return stockCreated;
    }

}
