package org.gym.facade;

import jakarta.validation.Valid;
import org.gym.dto.TraineeTrainingsDto;
import org.gym.dto.TrainingDto;

import java.time.LocalDate;
import java.util.List;

public interface TrainingFacade {
    TrainingDto create(TrainingDto trainingDto);
    List<TrainingDto> getTraineeTrainings(TraineeTrainingsDto traineeTrainingsDto);
    List<TrainingDto> getTrainerTrainings(String trainerUserName, LocalDate fromDate,
                                          LocalDate toDate, String trainerName);
}
