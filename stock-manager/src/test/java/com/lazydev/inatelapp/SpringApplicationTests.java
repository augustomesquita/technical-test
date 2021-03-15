package com.lazydev.inatelapp;

import com.lazydev.inatelapp.service.StockService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class SpringApplicationTests {

    @InjectMocks
    StockService stockService;


    @Before
    public void init() {
        initMocks(this);
    }

}

