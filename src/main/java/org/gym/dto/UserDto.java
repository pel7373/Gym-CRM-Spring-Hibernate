package org.gym.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotNull(message = "First name can't be null")
    @NotEmpty(message = "First name is required")
    @ToString.Exclude
    @Size(min = 4, message = "firstName should be min 4 chars")
    private String firstName;

    @NotNull(message = "Last name is required")
    @ToString.Exclude
    @Size(min = 4, message = "lastName should be min 4 chars")
    private String lastName;

    private String userName;

    @ToString.Exclude
    private String password;

    @NotNull(message = "isActive is required")
    private Boolean isActive;
}
