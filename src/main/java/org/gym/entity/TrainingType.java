package org.gym.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(schema = "gym", name = "training_types")
@Immutable
public class TrainingType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_type_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "training_type_name", nullable = false, unique = true)
    private String trainingTypeName;

    @ToString.Exclude
    @OneToOne(mappedBy = "specialization")
    private Trainer trainer;

    @ToString.Exclude
    @OneToMany(mappedBy = "trainingType")
    private List<Training> trainings;
}
