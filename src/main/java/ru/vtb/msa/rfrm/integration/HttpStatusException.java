package ru.vtb.msa.rfrm.integration;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpStatusException extends  RuntimeException {

    private HttpStatus status;
    private String body;

    public HttpStatusException(String message, String body, HttpStatus status) {
        super(message);
        this.status = status;
        this.body = body;
    }


}
