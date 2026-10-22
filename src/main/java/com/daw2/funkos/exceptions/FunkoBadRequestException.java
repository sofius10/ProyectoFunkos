package com.daw2.funkos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FunkoBadRequestException extends RuntimeException {
    public FunkoBadRequestException(String mensaje) { super(mensaje); }
}