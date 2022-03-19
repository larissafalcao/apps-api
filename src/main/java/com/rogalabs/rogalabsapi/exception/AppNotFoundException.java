package com.rogalabs.rogalabsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "app not found")
public class AppNotFoundException extends RuntimeException{
}
