package org.gym.repository.impl;

import lombok.AllArgsConstructor;
import org.gym.repository.TrainingRepository;
import org.gym.entity.Training;
import org.gym.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrainingRepositoryImpl implements TrainingRepository {
//    private final TrainingStorage trainingStorage;
//
    @Override
    public Training findById(Long id) throws EntityNotFoundException {
        return new Training();// trainingStorage.findById(id);
    }

    @Override
    public Training save(Training training) {
        return new Training(); //trainingStorage.save(training);
    }
}
