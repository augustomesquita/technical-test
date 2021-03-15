package com.lazydev.inatelapp.controller;

import com.lazydev.inatelapp.dto.StockQuoteRequest;
import com.lazydev.inatelapp.model.StockQuote;
import com.lazydev.inatelapp.service.StockQuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class StockQuoteController {

    @Autowired
    private StockQuoteService stockQuoteService;

    @GetMapping("/stock-quote")
    public List<StockQuote> getStocksQuotes() {
        log.info("Getting all stock quotes...");
        List<StockQuote> stocks = stockQuoteService.getStocks();
        log.info("All stock quotes available were returned successfully.");
        return stocks;
    }

    @GetMapping("/stock-quote/{id}")
    public StockQuote getStockQuoteById(@PathVariable String id) {
        log.info("Getting stock quote by id [{}]...", id);
        StockQuote stock = stockQuoteService.getStock(id);
        log.info("Stock quotes with id [{}] was returned successfully.", id);
        return stock;

    }

    @PostMapping("/stock-quote")
    @ResponseStatus(HttpStatus.CREATED)
    public StockQuote saveStockQuote(@Valid @RequestBody StockQuoteRequest stockQuoteRequest) {
        log.info("Creating stock quote...");
        StockQuote stockCreated = stockQuoteService.saveStock(stockQuoteRequest);
        log.info("Stock quote with id [{}] was created successfully.", stockCreated.getId());
        return stockCreated;
    }

}
