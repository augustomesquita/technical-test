package com.lazydev.inatelapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazydev.inatelapp.dto.StockQuoteRequest;
import com.lazydev.inatelapp.exception.CallApiException;
import com.lazydev.inatelapp.exception.InvalidCurrencyException;
import com.lazydev.inatelapp.exception.InvalidDateException;
import com.lazydev.inatelapp.exception.InvalidIdException;
import com.lazydev.inatelapp.exception.NotFoundException;
import com.lazydev.inatelapp.model.Quote;
import com.lazydev.inatelapp.model.StockQuote;
import com.lazydev.inatelapp.repository.StockQuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StockCacheService {

    List<String> availableStocks;

    public StockCacheService() {
        this.availableStocks = new ArrayList<>();
    }

    public List<String> getAvailableStocks() {
        return this.availableStocks;
    }

    public void clearAvailableStocks() {
        this.availableStocks.clear();
    }
}
