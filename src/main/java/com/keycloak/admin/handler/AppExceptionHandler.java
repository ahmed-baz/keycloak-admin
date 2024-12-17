package com.keycloak.admin.handler;


import com.keycloak.admin.dto.AppResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String defaultMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.BAD_REQUEST, defaultMessage);
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }
}
