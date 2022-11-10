package com.bootcamp.melifrescos.handler;

import com.bootcamp.melifrescos.exceptions.*;
import com.bootcamp.melifrescos.model.FiledError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.UnexpectedTypeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleValidPost(MethodArgumentNotValidException ex) {

        List<FiledError> errors = ex.getFieldErrors().stream()
                .map((erro) -> new FiledError(erro.getDefaultMessage()))
                        .collect(Collectors.toList());

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message("Argumentos da requisição inválidos.")
                .timesTemp(LocalDateTime.now())
                .errors(errors)
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleExceptions(NotFoundException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnavailableVolumeException.class)
    public ResponseEntity<ExceptionDetails> handleExceptions(UnavailableVolumeException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEnumTypeException.class)
    public ResponseEntity<ExceptionDetails> handleExceptions(InvalidEnumTypeException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidSectorTypeException.class)
    public ResponseEntity<ExceptionDetails> handleExceptions(InvalidSectorTypeException ex) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
