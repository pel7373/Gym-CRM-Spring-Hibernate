package org.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.core.annotation.Order;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "gym", name = "user")
//@ToString(exclude = {"firstName", "lastName", "password"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "user_id", nullable = false, unique = true)
    @Column(name = "user_id", unique = true)
    @Order(0)
    private long id;

    @ToString.Exclude
    //@Column(name = "first_name", nullable = false)
    @Column(name = "first_name")
    @Order(1)
    private String firstName;

    @ToString.Exclude
    //@Column(name = "last_name", nullable = false)
    @Column(name = "last_name")
    @Order(2)
    private String lastName;

    //@Column(name = "username", nullable = false, unique = true)
    @Column(name = "username", unique = true)
    @Order(3)
    private String userName;

    @ToString.Exclude
    //@Column(name = "password", nullable = false)
    @Column(name = "password")
    @Order(4)
    private String password;

    //@Column(name = "is_active", nullable = false)
    @Column(name = "is_active")
    @Order(5)
    private Boolean isActive;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Trainee trainee;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Trainer trainer;
}
