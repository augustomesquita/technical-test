package com.lazydev.inatelapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCurrencyException extends RuntimeException {

    public InvalidCurrencyException() {
        super("The stock has some quotes with invalid currency format.");
        log.error("Could no create a stock quote. It has some quotes with invalid currency format.");
    }
}
