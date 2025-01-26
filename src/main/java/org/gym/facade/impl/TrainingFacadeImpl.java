package org.gym.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            LOGGER.warn(ENTITY_CANT_BE_NULL, "create");
            return null;
        }

        if(!trainingDtoValidator.validate(trainingDto)) {
            LOGGER.warn(trainingDtoValidator.getErrorMessage(trainingDto));
            return null;
        }

        TrainingDto trainingDtoResult = null;
        try {
            trainingDtoResult = trainingService.create(trainingDto);
            LOGGER.trace("create: training with trainingName {} was created", trainingDtoResult.getTrainingName());
            return trainingDtoResult;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("create: trainee with userName {} doesn't found", trainingDto.getTrainee().getUser().getUserName());
        }
        return trainingDtoResult;
    }

    @Override
    public List<TrainingDto> getTraineeTrainings(String traineeUserName, LocalDate fromDate,
                                                 LocalDate toDate, String traineeName, String trainingType) {
        if (userNameAndPasswordChecker.isNullOrBlank(traineeUserName)) {
            LOGGER.warn(USERNAME_CANT_BE_NULL_OR_BLANK, "getTraineeTrainings", traineeUserName);
            return null;
        }

        List<TrainingDto> trainingDtoList = null;
        try {
            trainingDtoList = trainingService.getTraineeTrainingsListCriteria(traineeUserName,
                    fromDate, toDate, traineeName, trainingType);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("getTraineeTrainings: trainee {} doesn't found", traineeUserName);
        }
        return trainingDtoList;
    }

    @Override
    public List<TrainingDto> getTrainerTrainings(String trainerUserName, LocalDate fromDate,
                                                 LocalDate toDate, String trainerName) {
        if(userNameAndPasswordChecker.isNullOrBlank(trainerUserName)) {
            LOGGER.warn(USERNAME_CANT_BE_NULL_OR_BLANK, "getTraineeTrainings", trainerUserName);
            return null;
        }

        List<TrainingDto> trainingDtoList = null;
        try {
            trainingDtoList = trainingService.getTrainerTrainingsListCriteria(trainerUserName, fromDate, toDate, trainerName);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("getTrainerTrainings: trainer {} doesn't found", trainerUserName);
        }
        return trainingDtoList;
    }
}
