package com.lazydev.inatelapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidIdException extends RuntimeException {

    public InvalidIdException() {
        super("The id is invalid since is not present in the Stock Manager API.");
        log.error("Could no create a stock quote. The stock id is invalid since it`s register is not present in the Stock Manager API.");
    }
}
