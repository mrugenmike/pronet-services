package com.pronet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by neerajakukday on 3/18/15.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public final class BadRequestException extends IllegalArgumentException {

    public BadRequestException(String message) { super(message);}

}