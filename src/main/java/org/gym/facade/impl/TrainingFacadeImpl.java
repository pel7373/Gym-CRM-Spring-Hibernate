package org.gym.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.dto.TraineeTrainingsDto;
import org.gym.dto.TrainingDto;
import org.gym.exception.EntityNotFoundException;
import org.gym.facade.TrainingFacade;
import org.gym.service.TrainingService;
import org.gym.validator.TrainingDtoValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

import static org.gym.config.Config.*;

@Slf4j
@Component
@RequiredArgsConstructor
@Validated
public class TrainingFacadeImpl implements TrainingFacade {

    private final TrainingService trainingService;
    private final TrainingDtoValidator trainingDtoValidator;
    private final UserNameAndPasswordChecker userNameAndPasswordChecker;

    @Override
    public TrainingDto create(TrainingDto trainingDto) {
        if(trainingDto == null) {
            LOGGER.warn(ENTITY_CANT_BE_NULL,
                    Thread.currentThread().getStackTrace()[2].getMethodName());
            return null;
        }

        if(!trainingDtoValidator.validate(trainingDto)) {
            LOGGER.warn(trainingDtoValidator.getErrorMessage(trainingDto));
            return null;
        }

        TrainingDto trainingDtoResult = null;
        try {
            trainingDtoResult = trainingService.create(trainingDto);
            LOGGER.trace("{}: training with trainingName {} was created",
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    trainingDtoResult.getTrainingName());
            return trainingDtoResult;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("{}: trainee with userName {} doesn't found",
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    trainingDto.getTrainee().getUser().getUserName());
        }
        return trainingDtoResult;
    }

    @Override
    public List<TrainingDto> getTraineeTrainings(TraineeTrainingsDto traineeTrainingsDto) {
        if (userNameAndPasswordChecker.isNullOrBlank(traineeTrainingsDto.getTraineeUserName())) {
            LOGGER.warn(USERNAME_CANT_BE_NULL_OR_BLANK,
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    traineeTrainingsDto.getTraineeUserName());
            return null;
        }

        try {
            return trainingService.getTraineeTrainingsListCriteria(traineeTrainingsDto);
        } catch (EntityNotFoundException e) {
            LOGGER.warn(ENTITY_NOT_FOUND,
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    traineeTrainingsDto.getTraineeUserName());
            return null;
        }
    }

    @Override
    public List<TrainingDto> getTrainerTrainings(String trainerUserName, LocalDate fromDate,
                                                 LocalDate toDate, String trainerName) {
        if(userNameAndPasswordChecker.isNullOrBlank(trainerUserName)) {
            LOGGER.warn(USERNAME_CANT_BE_NULL_OR_BLANK, trainerUserName);
            return null;
        }

        try {
            return trainingService.getTrainerTrainingsListCriteria(trainerUserName, fromDate, toDate, trainerName);
        } catch (EntityNotFoundException e) {
            LOGGER.warn(ENTITY_NOT_FOUND, trainerUserName);
            return null;
        }
    }
}
