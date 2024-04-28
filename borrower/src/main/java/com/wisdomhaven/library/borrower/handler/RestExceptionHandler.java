package com.wisdomhaven.library.borrower.handler;


import com.wisdomhaven.library.borrower.dto.misc.ApiErrorMessage;
import com.wisdomhaven.library.borrower.dto.misc.ErrorMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        return buildResponseEntity("Malformed JSON request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<ErrorMessage> errorMessageList = ex.getConstraintViolations()
                .stream()
                .map(v -> ErrorMessage
                        .builder()
                        .propertyPath(v.getPropertyPath().toString())
                        .message(v.getMessage())
                        .build())
                .toList();
        ApiErrorMessage apiErrorMessage = ApiErrorMessage
                .builder()
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessages(errorMessageList)
                .build();
        return buildResponseEntity(apiErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex) {
        ApiErrorMessage apiErrorMessage = ApiErrorMessage
                .builder()
                .title(ex.getBody().getTitle())
                .statusCode(ex.getBody().getStatus())
                .message(ex.getReason())
                .build();

        return buildResponseEntity(apiErrorMessage, HttpStatus.valueOf(ex.getBody().getStatus()));
    }

    private <T> ResponseEntity<Object> buildResponseEntity(T apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
