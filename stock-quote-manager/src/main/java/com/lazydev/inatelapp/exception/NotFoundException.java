package com.lazydev.inatelapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Id not found.")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String id) {
        super(String.format("Could not find stock quote with id: [{%s}]", id));
        log.error(String.format("Could not find stock quote with id: [{%s}]", id));
    }
}
