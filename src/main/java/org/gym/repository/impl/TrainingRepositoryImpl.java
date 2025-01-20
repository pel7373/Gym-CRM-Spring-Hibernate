package org.gym.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.gym.repository.TrainingRepository;
import org.gym.entity.Training;
import org.gym.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TrainingRepositoryImpl implements TrainingRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Training save(Training training) {
        return new Training();
    }
}
