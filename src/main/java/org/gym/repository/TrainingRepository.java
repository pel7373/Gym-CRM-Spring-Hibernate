package org.gym.repository;

import org.gym.entity.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository  {
    Training save(Training training);
    List<Training> getByTraineeCriteria(String traineeUsername, LocalDate fromDate, LocalDate toDate,
                                        String trainerName, String trainingType);
    List<Training> getByTrainerCriteria(String trainerUsername, LocalDate fromDate, LocalDate toDate,
                                        String traineeName);
}
