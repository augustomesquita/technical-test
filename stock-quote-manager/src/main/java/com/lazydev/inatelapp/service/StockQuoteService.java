package com.lazydev.inatelapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazydev.inatelapp.dto.StockQuoteRequest;
import com.lazydev.inatelapp.exception.CallApiException;
import com.lazydev.inatelapp.exception.InvalidCurrencyException;
import com.lazydev.inatelapp.exception.InvalidDateException;
import com.lazydev.inatelapp.exception.NotFoundException;
import com.lazydev.inatelapp.model.Quote;
import com.lazydev.inatelapp.model.StockQuote;
import com.lazydev.inatelapp.repository.StockQuoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockQuoteService {

    @Autowired
    private StockQuoteRepository stockQuoteRepository;

    @Autowired
    private StockCacheService stockCacheService;

    @Value("${application.stockmanager.host}")
    private String host;

    @Value("${application.stockmanager.port}")
    private Integer port = 8081;

    public List<StockQuote> getStocks() {
        return stockQuoteRepository.findAll();
    }

    public StockQuote getStock(String id) {
        return stockQuoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public StockQuote saveStock(StockQuoteRequest stockQuoteRequest) {
        validateStock(stockQuoteRequest.getId());
        validateQuotes(stockQuoteRequest.getQuotes());
        StockQuote build = StockQuote.builder()
                .id(stockQuoteRequest.getId().toLowerCase())
                .quotes(extractQuotesFromRequest(stockQuoteRequest))
                .build();


        return stockQuoteRepository.save(build);
    }

    private void validateStock(String id)  {
        Set<String> availableStocks = stockCacheService.getAvailableStocks();
        if (availableStocks.isEmpty()) {
            try {
                log.info("Stock Cache is empty. Retrieving registers from Stock Manager API...");
                URL url = new URL(String.format("http://%s:%d/api/stock", host, port));
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept", "application/json");
                InputStream responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                Set<String> stocks = mapper.readValue(responseStream, new TypeReference<Set<String>>(){});
                if (!stocks.contains(id)) {
                    throw new NotFoundException(id);
                }
                log.info("Stock with id [{}] is available ", id);
                stockCacheService.addAll(stocks);
            } catch (IOException e) {
                throw new CallApiException();
            }
        } else {
            log.info("Verifying stocks available through the Stock Cache...");
            if (!availableStocks.contains(id)) {
                throw new NotFoundException(id);
            }
            log.info("Stock with id [{}] is available ", id);
        }

    }

    private void validateQuotes(Map<String, String> quotes) {
        final Pattern datePattern = Pattern.compile("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$");
        final Pattern currencyPattern = Pattern.compile("^[0-9]*[,-\\.]?[0-9]+$");

        quotes.forEach((quotationDate, quotationValue) -> {
            if (!datePattern.matcher(quotationDate).matches()) {
                throw new InvalidDateException();
            }

            if (!currencyPattern.matcher(quotationValue).matches()) {
                throw new InvalidCurrencyException();
            }
        });
    }

    private List<Quote> extractQuotesFromRequest(StockQuoteRequest stockQuoteRequest) {
        return stockQuoteRequest.getQuotes().entrySet().stream()
                .map(quote -> Quote.builder()
                        .stockQuote(StockQuote.builder().id(stockQuoteRequest.getId()).build())
                        .quotationDate(quote.getKey())
                        .quotationValue(quote.getValue())
                        .build()
                ).collect(Collectors.toList());
    }

}
