package com.scrabble.calculator.filter;

import com.scrabble.calculator.dto.response.ErrorMessage;
import com.scrabble.calculator.exception.InvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidException(InvalidException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());

        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleOtherException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage("Service encounter internal error.");

        return message;
    }
}
