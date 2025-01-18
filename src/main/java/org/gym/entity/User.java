package org.gym.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "user_id", nullable = false, unique = true)
    @Column(name = "id", unique = true)
    private Long id;

    @ToString.Exclude
    //@Column(name = "first_name", nullable = false)
    @Column(name = "first_name")
    @Min(value = 4, message = "firstName should be min 4 chars")
    private String firstName;

    @ToString.Exclude
    //@Column(name = "last_name", nullable = false)
    @Column(name = "last_name")
    private String lastName;

    //@Column(name = "username", nullable = false, unique = true)
    //@Column(name = "username", unique = true)
    @Column(name = "username")
    private String userName;

    @ToString.Exclude
    //@Column(name = "password", nullable = false)
    @Column(name = "password")
    private String password;

    //@Column(name = "is_active", nullable = false)
    @Column(name = "is_active")
    private Boolean isActive;
}
