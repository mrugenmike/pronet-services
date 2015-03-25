package com.pronet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public final class BadRequestException extends IllegalArgumentException {

    public BadRequestException(String message) { super(message);}

}