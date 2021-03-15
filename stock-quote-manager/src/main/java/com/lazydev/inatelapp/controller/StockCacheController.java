package com.lazydev.inatelapp.controller;

import com.lazydev.inatelapp.dto.StockQuoteRequest;
import com.lazydev.inatelapp.model.StockQuote;
import com.lazydev.inatelapp.service.StockCacheService;
import com.lazydev.inatelapp.service.StockQuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class StockCacheController {

    @Autowired
    private StockCacheService stockCacheService;

    @DeleteMapping("/stockcache")
    public ResponseEntity<String> getStocksQuotes() {
        log.info("Cleaning stock cache...");
        stockCacheService.clearAvailableStocks();
        log.info("Stock Cache cleaned successfully.");
        return ResponseEntity.ok("Stock cache cleaned.");
    }

}
