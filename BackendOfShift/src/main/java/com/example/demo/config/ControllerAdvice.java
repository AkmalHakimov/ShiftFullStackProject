package com.example.demo.config;

import io.jsonwebtoken.io.IOException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.io.FileNotFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IOException.class)
    public HttpEntity<?> IOException(java.io.IOException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public HttpEntity<?> fileNotFound(FileNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public HttpEntity<?> NullPointer(NullPointerException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public HttpEntity<?> IndexOutOfBound(IndexOutOfBoundsException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
