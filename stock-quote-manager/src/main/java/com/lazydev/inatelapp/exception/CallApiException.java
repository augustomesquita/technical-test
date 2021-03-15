package com.lazydev.inatelapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CallApiException extends RuntimeException {

    public CallApiException() {
        super("Error while trying to reach the Stock Manager API");
        log.error("Could no create a stock quote. Error while trying to reach the Stock Manager API.");
    }
}
