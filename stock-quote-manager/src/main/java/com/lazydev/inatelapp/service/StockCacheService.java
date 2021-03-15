package com.lazydev.inatelapp.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class StockCacheService {

    private final CopyOnWriteArraySet<String> availableStocks;

    public StockCacheService() {
        this.availableStocks = new CopyOnWriteArraySet<>();
    }

    public Set<String> getAvailableStocks() {
        return new HashSet<>(this.availableStocks);
    }

    public void addAll(Set<String> availableStocks) {
        this.availableStocks.addAll(availableStocks);
    }

    public void clearAvailableStocks() {
        this.availableStocks.clear();
    }
}
