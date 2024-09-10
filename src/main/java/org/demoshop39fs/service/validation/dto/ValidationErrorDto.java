package org.demoshop39fs.service.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationErrorDto {

    private String field;
    private String rejectedValue;
    private String message;
}
