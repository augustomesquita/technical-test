package com.lazydev.inatelapp;

import com.lazydev.inatelapp.dto.StockQuoteRequest;
import com.lazydev.inatelapp.exception.InvalidCurrencyException;
import com.lazydev.inatelapp.exception.InvalidDateException;
import com.lazydev.inatelapp.exception.InvalidIdException;
import com.lazydev.inatelapp.model.Quote;
import com.lazydev.inatelapp.model.StockQuote;
import com.lazydev.inatelapp.repository.StockQuoteRepository;
import com.lazydev.inatelapp.service.StockCacheService;
import com.lazydev.inatelapp.service.StockQuoteService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class SpringApplicationTests {

    @InjectMocks
    StockQuoteService stockQuoteService;

    @Mock
    StockCacheService stockCacheService;

    @Mock
    StockQuoteRepository stockQuoteRepository;

    @Rule
    public ExpectedException exceptionRule;

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void getAllStockQuotes() {
        // Given
        List<Quote> quotes1 = new ArrayList<>();
        List<StockQuote> stockMocks = new ArrayList<>();
        stockMocks.add(new StockQuote("petr3", quotes1));
        stockMocks.add(new StockQuote("vale5", quotes1));

        // When
        when(stockQuoteRepository.findAll()).thenReturn(stockMocks);
        List<StockQuote> stocks = stockQuoteService.getStocks();

        // Then
        assertEquals(2, stocks.size());
        verify(stockQuoteRepository, times(1)).findAll();
    }

    @Test
    public void saveStockQuote_InvalidIdException() {
        // Given
        StockQuoteRequest stockQuoteRequest = new StockQuoteRequest();
        stockQuoteRequest.setId("petr5");

        HashSet<String> availableStocks = new HashSet<>();
        availableStocks.add("petr3");

        // When
        when(stockCacheService.getAvailableStocks()).thenReturn(availableStocks);

        // Then
        assertThrows(InvalidIdException.class, () -> {
            stockQuoteService.saveStock(stockQuoteRequest);
        });
    }

    @Test
    public void saveStockQuote_InvalidCurrencyException() {
        // Given
        Map<String, String> quotes = new HashMap<>();
        quotes.put("2019-01-01", "10,50.0");

        StockQuoteRequest stockQuoteRequest = new StockQuoteRequest();
        stockQuoteRequest.setId("petr3");
        stockQuoteRequest.setQuotes(quotes);

        HashSet<String> availableStocks = new HashSet<>();
        availableStocks.add("petr3");

        // When
        when(stockCacheService.getAvailableStocks()).thenReturn(availableStocks);

        // Then
        assertThrows(InvalidCurrencyException.class, () -> {
            stockQuoteService.saveStock(stockQuoteRequest);
        });
    }

    @Test
    public void saveStockQuote_InvalidCurrency2Exception() {
        // Given
        Map<String, String> quotes = new HashMap<>();
        quotes.put("2019-01-30", "10.50.0");

        StockQuoteRequest stockQuoteRequest = new StockQuoteRequest();
        stockQuoteRequest.setId("petr3");
        stockQuoteRequest.setQuotes(quotes);

        HashSet<String> availableStocks = new HashSet<>();
        availableStocks.add("petr3");

        // When
        when(stockCacheService.getAvailableStocks()).thenReturn(availableStocks);

        // Then
        assertThrows(InvalidCurrencyException.class, () -> {
            stockQuoteService.saveStock(stockQuoteRequest);
        });
    }

    @Test
    public void saveStockQuote_InvalidDateException() {
        // Given
        Map<String, String> quotes = new HashMap<>();
        quotes.put("2019-30-01", "10,50");

        StockQuoteRequest stockQuoteRequest = new StockQuoteRequest();
        stockQuoteRequest.setId("petr3");
        stockQuoteRequest.setQuotes(quotes);

        HashSet<String> availableStocks = new HashSet<>();
        availableStocks.add("petr3");

        // When
        when(stockCacheService.getAvailableStocks()).thenReturn(availableStocks);

        // Then
        assertThrows(InvalidDateException.class, () -> {
            stockQuoteService.saveStock(stockQuoteRequest);
        });
    }

    @Test
    public void saveStockQuote_InvalidDate2Exception() {
        // Given
        Map<String, String> quotes = new HashMap<>();
        quotes.put("2019-01-01-", "10,50");

        StockQuoteRequest stockQuoteRequest = new StockQuoteRequest();
        stockQuoteRequest.setId("petr3");
        stockQuoteRequest.setQuotes(quotes);

        HashSet<String> availableStocks = new HashSet<>();
        availableStocks.add("petr3");

        // When
        when(stockCacheService.getAvailableStocks()).thenReturn(availableStocks);

        // Then
        assertThrows(InvalidDateException.class, () -> {
            stockQuoteService.saveStock(stockQuoteRequest);
        });
    }

}

