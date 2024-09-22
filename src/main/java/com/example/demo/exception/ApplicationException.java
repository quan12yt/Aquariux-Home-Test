package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationException extends Exception {

    private HttpStatus statusCode;

    public ApplicationException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
