package org.gym.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingTypeDto {

    @Pattern(regexp = "^[A-Z][a-z]+$", message = "Training type name name mustn't contain numbers")
    @NotNull(message = "Training type name cannot be null")
    private String trainingTypeName;
}
