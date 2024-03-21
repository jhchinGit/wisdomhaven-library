package com.wisdomhaven.library.global;

import com.wisdomhaven.library.dto.misc.ApiErrorMessage;
import com.wisdomhaven.library.dto.misc.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
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

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiErrorMessage apiErrorMessage = ApiErrorMessage
                .builder()
                .title(HttpStatus.NOT_FOUND.getReasonPhrase())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return buildResponseEntity(apiErrorMessage, HttpStatus.NOT_FOUND);
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

    private <T> ResponseEntity<Object> buildResponseEntity(T apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
