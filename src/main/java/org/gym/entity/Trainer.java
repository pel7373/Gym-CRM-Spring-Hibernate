package org.gym.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainers")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@ToString
public class Trainer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id", nullable = false, unique = true)
    @Column(name = "id", unique = true)
    private Long id;

    @OneToOne
    //@JoinColumn(name = "specialization", referencedColumnName = "id", nullable = false)
    @JoinColumn(name = "specialization", referencedColumnName = "id")
    private TrainingType specialization;

    @OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_id", nullable = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainings;

    @ToString.Exclude
    @ManyToMany(mappedBy = "trainers", fetch = FetchType.LAZY)
    private List<Trainee> trainees = new ArrayList<>();
}
