package org.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {

    @NotNull(message = "First name is required")
    @ToString.Exclude
    private String firstName;

    @NotNull(message = "Last name is required")
    @ToString.Exclude
    private String lastName;

    private String username;

    @ToString.Exclude
    private String password;

    @NotNull(message = "isActive is required")
    private Boolean isActive;
}
