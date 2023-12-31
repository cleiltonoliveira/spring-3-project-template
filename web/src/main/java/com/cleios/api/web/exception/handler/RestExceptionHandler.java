package com.cleios.api.web.exception.handler;

import com.cleios.api.web.exception.ErrorDetailsResponse;
import com.cleios.api.usecases.exception.ResourceConflictException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<?> handleResourceConflictException(ResourceConflictException exception, WebRequest request) {
        var errorDetails = buildErrorDetails(
                "Resource conflict",
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(errorDetails.getHttpStatusCode()));
    }




    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        var fieldErrors = exception.getBindingResult().getFieldErrors();

        var validationErrorDetails = buildValidationErrorDetails(
                "Validation error",
                status.value(),
                "Validation failed for one or more fields",
                request.getDescription(false),
                fieldErrors
        );
        return new ResponseEntity<>(validationErrorDetails, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {

        var message = exception.getCause() != null ? exception.getCause().getMessage() : "";

        var errorDetails = buildErrorDetails(
                "Request payload not readable",
                status.value(),
                message,
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, headers, status);
    }

    public ErrorDetailsResponse buildErrorDetails(String error, int status, String message, String path) {
        return ErrorDetailsResponse.builder()
                .error(error)
                .httpStatusCode(status)
                .timestamp(OffsetDateTime.now())
                .message(message)
                .path(path.replace("uri=", ""))
                .build();
    }

    public ErrorDetailsResponse buildValidationErrorDetails(String error, int status, String message, String path, List<FieldError> fieldErrors) {

        Map<String, String> fieldErrorsMap = fieldErrors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        var errorDetails = buildErrorDetails(error, status, message, path);
        errorDetails.setFieldErrors(fieldErrorsMap);

        return errorDetails;
    }
}