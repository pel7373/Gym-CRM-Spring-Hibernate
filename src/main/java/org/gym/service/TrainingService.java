package org.gym.service;

import org.gym.dto.TrainingDto;
import org.gym.entity.Training;
import org.gym.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService extends GenericService<TrainingDto> {
    List<TrainingDto> getTraineeTrainingsListCriteria(String traineeUserName, LocalDate fromDate,
                                                      LocalDate toDate, String trainerName, String trainingType);
    List<TrainingDto> getTrainerTrainingsListCriteria(String trainerUsername, LocalDate fromDate,
                                                      LocalDate toDate, String traineeName);
}
