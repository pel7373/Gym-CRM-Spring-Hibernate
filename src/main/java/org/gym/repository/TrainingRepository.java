package org.gym.repository;

import org.gym.dto.TraineeTrainingsDto;
import org.gym.entity.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository  {
    Training save(Training training);
    List<Training> getByTraineeCriteria(TraineeTrainingsDto traineeTrainingsDto);
    List<Training> getByTrainerCriteria(String trainerUsername, LocalDate fromDate, LocalDate toDate,
                                        String traineeName);
}
