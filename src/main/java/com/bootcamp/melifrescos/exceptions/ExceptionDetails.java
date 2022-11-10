package com.bootcamp.melifrescos.exceptions;

import com.bootcamp.melifrescos.model.FiledError;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetails {
    private String title;
    private String message;
    private LocalDateTime timesTemp;
    private List<FiledError> errors;
}
