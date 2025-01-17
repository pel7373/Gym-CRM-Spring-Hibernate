package org.gym.facade;

import org.gym.dto.TrainingDto;

import java.time.LocalDate;
import java.util.List;

public interface TrainingFacade {
    TrainingDto addTraining(TrainingDto trainingDto);
    List<TrainingDto> getTraineeTrainings(String traineeUsername, LocalDate fromDate,
                                          LocalDate toDate, String traineeName, String trainingType);
    List<TrainingDto> getTrainerTrainings(String trainerUsername, LocalDate fromDate,
                                          LocalDate toDate, String trainerName);
}
