package org.gym.service;

import jakarta.validation.Valid;
import org.gym.dto.TrainingDto;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService extends GenericService<TrainingDto> {
    List<TrainingDto> getTraineeTrainingsListCriteria(String traineeUserName, LocalDate fromDate,
                                                      LocalDate toDate, String trainerName, String trainingType);
    List<TrainingDto> getTrainerTrainingsListCriteria(String trainerUsername, LocalDate fromDate,
                                                      LocalDate toDate, String traineeName);
}
