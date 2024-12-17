package com.keycloak.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppResponse<T> {
    private Date responseDate;
    private Integer statusCode;
    private String message;
    private T data;

    public AppResponse() {
        this.responseDate = new Date();
        this.statusCode = HttpStatus.OK.value();
    }

    public AppResponse(HttpStatus status) {
        this.responseDate = new Date();
        this.statusCode = status.value();
    }

    public AppResponse(T t) {
        this.data = t;
        this.responseDate = new Date();
        this.statusCode = HttpStatus.OK.value();
    }

    public AppResponse(T t, HttpStatus status) {
        this.data = t;
        this.responseDate = new Date();
        this.statusCode = status.value();
    }

    public AppResponse(HttpStatus status, String message) {
        this.responseDate = new Date();
        this.statusCode = status.value();
        this.message = message;
    }

}
