package com.bootcamp.melifrescos.handler;

import com.bootcamp.melifrescos.exceptions.*;
import com.bootcamp.melifrescos.model.FiledError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FiledError> errors = ex.getFieldErrors().stream()
                .map((erro) -> new FiledError(erro.getDefaultMessage()))
                        .collect(Collectors.toList());

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message("Argumentos inválidos.")
                .timesTemp(LocalDateTime.now())
                .errors(errors)
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerNotFoundException(NotFoundException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("NOT FOUND")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnavailableVolumeException.class)
    public ResponseEntity<ExceptionDetails> handlerUnavailableVolumeException(UnavailableVolumeException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEnumTypeException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidEnumTypeException(InvalidEnumTypeException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSectorTypeException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidSectorTypeException(InvalidSectorTypeException ex) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PurchaseAlreadyFinishedException.class)
    public ResponseEntity<ExceptionDetails> handlerPurchaseAlreadyFinishedException(PurchaseAlreadyFinishedException ex) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoQuantityBatchProduct.class)
    public ResponseEntity<ExceptionDetails> handlerPurchaseAlreadyFinishedException(NoQuantityBatchProduct ex) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title("BAD REQUEST")
                .message(ex.getMessage())
                .timesTemp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
