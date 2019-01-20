package com.efgh.controller;

import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

/**
 * Exception handler in charge of returning custom responses given the detected errors
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody ApiError handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException ex) {
        return new ApiError(ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public @ResponseBody ApiError handleNoSuchElementException(HttpServletRequest req, NoSuchElementException ex) {
        return new ApiError(ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody ApiError handleGeneralException(HttpServletRequest req, Exception ex) {
        return new ApiError(ex);
    }

    @Data
    class ApiError{
        String errorMessage;
        String errorCause;
        String errorStack;

        ApiError(Exception e){
            Assert.notNull(e, "Null exception received");
            setErrorMessage(ExceptionUtils.getMessage(e));
            setErrorCause(ExceptionUtils.getRootCauseMessage(e));
            setErrorStack(ExceptionUtils.getStackTrace(e));
        }
    }
}
