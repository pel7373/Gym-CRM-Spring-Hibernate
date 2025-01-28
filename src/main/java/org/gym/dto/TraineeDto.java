package org.gym.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeDto {

    @NotNull(message = "UserDto is required")
    private UserDto user;

    @ToString.Exclude
    private LocalDate dateOfBirth;

    @ToString.Exclude
    private String address;
}
