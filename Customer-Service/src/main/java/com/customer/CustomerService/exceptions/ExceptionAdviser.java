package com.customer.CustomerService.exceptions;

import com.cartservice.CartService.exceptions.CartDetailsNotFound;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.orders.OrdersService.exceptions.ExceptionMessage;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdviser {

    @ExceptionHandler(CartServiceUpdationException.class)
    public ResponseEntity<ErrorMessage> exceptionHandling1(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomerDetailsNotAvailable.class)
    public ResponseEntity<ErrorMessage> exceptionHandling2(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(AddressNotFoundWithEmail.class)
//    public ResponseEntity<ErrorMessage> exceptionHandling3(Exception exception) {
//        ErrorMessage errorMessage = new ErrorMessage();
//        errorMessage.setErrorMessage(exception.getMessage());
//        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(CartDetailsNotFoundException.class)
    public ResponseEntity<ErrorMessage> exceptionHandling4(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrdersNotPlacedException.class)
    public ResponseEntity<ExceptionMessage> ordersException1(Exception exception){

        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setExceptionMessage(exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }



}
