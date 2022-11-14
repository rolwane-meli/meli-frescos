package com.bootcamp.melifrescos.exceptions;

import com.bootcamp.melifrescos.model.FiledError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionDetails {
    private String title;
    private String message;
    private LocalDateTime timesTemp;
    private List<FiledError> errors;
}
