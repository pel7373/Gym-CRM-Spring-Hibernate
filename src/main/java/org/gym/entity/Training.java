package org.gym.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

//@Entity
//@Table(schema = "gym", name = "trainings")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Training implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "training_id", nullable = false, unique = true)
    @Column(name = "training_id", unique = true)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    //@Column(name = "training_name", nullable = false)
    @Column(name = "training_name")
    private String trainingName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    //@Column(name = "date", nullable = false)
    @Column(name = "date")
    private LocalDate date;

    //@Column(name = "duration", nullable = false)
    @Column(name = "duration")
    private Integer duration;
}
