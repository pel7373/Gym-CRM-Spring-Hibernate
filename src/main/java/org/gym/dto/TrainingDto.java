package org.gym.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingDto {

    @NotNull(message = "Trainee id is required")
    private TraineeDto trainee;

    @NotNull(message = "Trainer id is required")
    private TrainerDto trainer;

    @Pattern(regexp = "^[A-Z][a-z.:0-9]+$", message = "Training name must start with a capital letter and then contain only lowercase letters, dots and colons")
    @NotNull(message = "Training name is required")
    private String trainingName;

    @NotNull(message = "Training type is required")
    private TrainingTypeDto trainingType;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Training date should be in the present or future")
    private LocalDate date;

    @NotNull(message = "Duration is required")
    @Positive(message = "Training duration must be positive")
    private Integer duration;
}
