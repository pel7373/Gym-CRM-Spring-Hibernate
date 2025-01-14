package org.gym.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "gym", name = "trainer")
@EqualsAndHashCode//(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@ToString//(callSuper = true)
public class Trainer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "trainer_id", nullable = false, unique = true)
    @Column(name = "trainer_id", unique = true)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_id", nullable = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    //@JoinColumn(name = "specialization", referencedColumnName = "training_type_id", nullable = false)
    @JoinColumn(name = "specialization", referencedColumnName = "training_type_id")
    private TrainingType specialization;

//    @ToString.Exclude
//    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Training> trainings;
//
//    @ToString.Exclude
//    @ManyToMany(mappedBy = "trainers", fetch = FetchType.LAZY)
//    private List<Trainee> trainees = new ArrayList<>();
}
