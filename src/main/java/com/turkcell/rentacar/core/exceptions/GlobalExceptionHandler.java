package com.turkcell.rentacar.core.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.turkcell.rentacar.core.utilities.results.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.turkcell.rentacar.core.utilities.results.ErrorDataResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ErrorDataResult<>(validationErrors, "Validation Error.");
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleBusinessException(BusinessException businessException) {

        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put(businessException.getMessage(), "Business Exception");

        return new ErrorDataResult<>(validationErrors, "Business Exception");

    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResult handleDataException() {

        return new ErrorResult("Unexpected error.");
    }

}
