package org.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerDto implements Serializable {

    @NotNull(message = "User cannot be null")
    private UserDto user;

    @NotNull(message = "specialization is required")
    private TrainingTypeDto specialization;
}
