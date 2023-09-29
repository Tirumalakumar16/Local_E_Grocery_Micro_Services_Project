package com.shop.ShopService.exceptions;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorAdviser {
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorMessage> exceptionHandling( Exception notFound) {

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(notFound.getMessage());
        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotAutherizedException.class)
    public ResponseEntity<ErrorMessage> exception2(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(errorMessage,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ShopIsNotFoundException.class)
    public ResponseEntity<ErrorMessage> exception3(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }
}
