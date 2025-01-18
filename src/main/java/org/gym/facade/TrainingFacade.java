package org.gym.facade;

import org.gym.dto.TrainingDto;
import org.gym.entity.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingFacade extends GenericFacade<TrainingDto> {
    List<TrainingDto> getTraineeTrainings(String traineeUserName, LocalDate fromDate,
                                          LocalDate toDate, String traineeName, String trainingType);
    List<TrainingDto> getTrainerTrainings(String trainerUserName, LocalDate fromDate,
                                          LocalDate toDate, String trainerName);
}
