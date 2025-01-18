package org.gym.facade.impl;

import lombok.RequiredArgsConstructor;
import org.gym.dto.TrainingDto;
import org.gym.entity.Training;
import org.gym.facade.TrainingFacade;
import org.gym.service.TrainingService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingFacadeImpl implements TrainingFacade {

    private final TrainingService trainingService;

    @Override
    public TrainingDto create(TrainingDto trainingDto) {
        return trainingService.create(trainingDto);
    }

    @Override
    public List<TrainingDto> getTraineeTrainings(String traineeUserName, LocalDate fromDate,
                                                 LocalDate toDate, String traineeName, String trainingType) {
        return trainingService.getTraineeTrainingsListCriteria(traineeUserName, fromDate, toDate, traineeName, trainingType);
    }

    @Override
    public List<TrainingDto> getTrainerTrainings(String trainerUserName, LocalDate fromDate,
                                                 LocalDate toDate, String trainerName) {
        return trainingService.getTrainerTrainingsListCriteria(trainerUserName, fromDate, toDate, trainerName);
    }
}
