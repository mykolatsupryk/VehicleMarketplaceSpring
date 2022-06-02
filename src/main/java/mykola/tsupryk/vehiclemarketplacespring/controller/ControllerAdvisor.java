package mykola.tsupryk.vehiclemarketplacespring.controller;

import mykola.tsupryk.vehiclemarketplacespring.dto.response.ErrorResponse;
import mykola.tsupryk.vehiclemarketplacespring.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.message.AuthException;

import java.net.MalformedURLException;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(InvalidContactNumberException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleInvalidContactNumberException (InvalidContactNumberException numberException) {
        return ErrorResponse.builder()
                .message(numberException.getMessage())
                .status(BAD_REQUEST)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException (NotFoundException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(NOT_FOUND)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(UnreachebleTypeException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleUnreachebleTypeException (UnreachebleTypeException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(NOT_FOUND)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(NoMoneyException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleNoMoneyException (NoMoneyException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(NOT_FOUND)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleInvalidEmailException (InvalidEmailException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(NOT_FOUND)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(NOT_FOUND)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(MalformedURLException.class)
    @ResponseStatus(UNSUPPORTED_MEDIA_TYPE)
    public ErrorResponse handleMalformedURLException(MalformedURLException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(UNSUPPORTED_MEDIA_TYPE)
                .timestamp(now())
                .build();
    }


}
