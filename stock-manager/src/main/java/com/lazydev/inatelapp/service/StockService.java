package com.lazydev.inatelapp.service;

import com.lazydev.inatelapp.dto.StockRequest;
import com.lazydev.inatelapp.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class StockService {

    @Autowired
    NotificationService notificationService;

    CopyOnWriteArraySet<Stock> stocks;

    public StockService() {
        this.stocks = new CopyOnWriteArraySet<>();
        this.stocks.add(Stock.builder().id("petr3").description("test petr").build());
        this.stocks.add(Stock.builder().id("vale5").description("test vale").build());
    }

    public List<Stock> getStocks() {
        return new ArrayList<>(this.stocks);
    }

    public Stock saveStock(StockRequest stockRequest) {
        Stock stock = Stock.builder()
                .id(stockRequest.getId().toLowerCase())
                .description(stockRequest.getDescription())
                .build();
        this.stocks.add(stock);
        notificationService.notifyClients();
        return stock;
    }
}
