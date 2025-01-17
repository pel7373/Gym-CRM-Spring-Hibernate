package org.gym.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingFacadeImpl implements TrainingFacade {

    private final TrainingService trainingService;

    @Override
    public TrainingDto addTraining(TrainingDto trainingDto) {
        return trainingService.add(trainingDto);
    }

    @Override
    public List<TrainingDto> getTraineeTrainings(String traineeUsername, LocalDate fromDate,
                                                 LocalDate toDate, String traineeName, String trainingType) {
        return trainingService.getTraineeTrainingsListCriteria(traineeUsername, fromDate, toDate, traineeName, trainingType);
    }

    @Override
    public List<TrainingDto> getTrainerTrainings(String trainerUsername, LocalDate fromDate,
                                                 LocalDate toDate, String trainerName) {
        return trainingService.getTrainerTrainingsListCriteria(trainerUsername, fromDate, toDate, trainerName);
    }
}
