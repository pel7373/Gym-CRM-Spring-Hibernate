package org.gym.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.springframework.core.annotation.Order;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "gym", name = "trainee")
//@EqualsAndHashCode//(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
//@ToString(callSuper = true)
public class Trainee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "trainee_id", nullable = false, unique = true)
    @Column(name = "trainee_id", unique = true)
    private Long id;

    @ToString.Exclude
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ToString.Exclude
    @Column(name = "address")
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_id", nullable = false)
    @JoinColumn(name = "user_id")
    private User user;

//    @ToString.Exclude
//    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Training> trainings;

//    @ToString.Exclude
//    @ManyToMany(cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//    })
//    @JoinTable(
//            name = "trainee_trainer",
//            joinColumns = @JoinColumn(name = "trainee_id", referencedColumnName = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "trainer_id", referencedColumnName = "user_id")
//    )
//    private List<Trainer> trainers = new ArrayList<>();
}
