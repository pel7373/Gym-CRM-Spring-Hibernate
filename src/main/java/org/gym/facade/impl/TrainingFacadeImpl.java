package org.gym.facade.impl;

import lombok.RequiredArgsConstructor;
import org.gym.dto.TraineeDto;
import org.gym.dto.TrainingDto;
import org.gym.entity.Training;
import org.gym.facade.TrainingFacade;
import org.gym.service.TrainingService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static org.gym.config.AppConfig.ENTITY_CANT_BE_NULL;

@Component
@RequiredArgsConstructor
public class TrainingFacadeImpl implements TrainingFacade {

    private final TrainingService trainingService;

    @Override
    public TrainingDto create(TrainingDto trainingDto) {
        if(trainingDto == null) {
            LOGGER.warn(ENTITY_CANT_BE_NULL, "create");
            return null;
        }

        if(!userDtoValidator.validate(traineeDto.getUser())) {
            LOGGER.warn(userDtoValidator.getErrorMessage(traineeDto.getUser()));
            return null;
        }

        TraineeDto traineeDtoResult = traineeService.create(traineeDto);
        LOGGER.trace("create: {} was created", traineeDtoResult);
        return traineeDtoResult;
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
