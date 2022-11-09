package com.bootcamp.melifrescos.handler;

import com.bootcamp.melifrescos.exceptions.ExceptionDetails;
import com.bootcamp.melifrescos.model.FiledError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleValidPost(MethodArgumentNotValidException ex) {

        List<FiledError> errors = ex.getFieldErrors().stream()
                .map((erro) -> new FiledError(erro.getField(), erro.getDefaultMessage(), Objects.requireNonNull(erro.getRejectedValue())
                        .toString())).collect(Collectors.toList());

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message("Argumentos inválidos.")
                .errors(errors)
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
