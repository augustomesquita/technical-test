package com.lazydev.inatelapp;

import com.lazydev.inatelapp.repository.StockQuoteRepository;
import com.lazydev.inatelapp.service.StockQuoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class SpringApplicationTests {

    @InjectMocks
    StockQuoteService stockQuoteService;

    @Mock
    Pageable pageableMock;

    @Mock
    StockQuoteRepository stockQuoteRepository;

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void getAllUsersPageable() {
        // Given
//        List<Stock> stocks = new ArrayList<>();
//        stocks.add(new Stock(1L, "test1", "test1@email.com"));
//        stocks.add(new Stock(2L, "test2", "test2@email.com"));
//        stocks.add(new Stock(3L, "test3", "test3@email.com"));
//        Page<Stock> usersPageable = new PageImpl(stocks);

        // When
//        when(userRepository.findAll(pageableMock)).thenReturn(usersPageable);
//        Page<Stock> allUsers = userService.getUsersPageable(pageableMock);

        // Then
//        assertEquals(3, allUsers.getTotalElements());
//        verify(userRepository, times(1)).findAll(pageableMock);
    }

}

