package org.gym.service;

import org.gym.dto.TraineeTrainingsDto;
import org.gym.dto.TrainingDto;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {
    TrainingDto create(TrainingDto trainingDto);
    List<TrainingDto> getTraineeTrainingsListCriteria(TraineeTrainingsDto traineeTrainingsDto);
    List<TrainingDto> getTrainerTrainingsListCriteria(String trainerUsername, LocalDate fromDate,
                                                      LocalDate toDate, String traineeName);
}
