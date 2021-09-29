package com.SimbirSoft.Practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenActionExceptions extends RuntimeException {
    public ForbiddenActionExceptions(String message) {
        super(message);
    }
}
