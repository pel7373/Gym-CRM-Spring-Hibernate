package org.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerDto {

    @NotNull(message = "User cannot be null")
    private UserDto user;

    @NotNull(message = "specialization is required")
    private TrainingTypeDto specialization;
}
