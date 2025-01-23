package org.gym.facade.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.dto.TrainerDto;
import org.gym.entity.TrainingType;
import org.gym.facade.TrainerFacade;
import org.gym.service.TrainerService;
import org.gym.validator.UserDtoValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static org.gym.config.Config.*;

@Slf4j
@Component
@RequiredArgsConstructor
@Validated
public class TrainerFacadeImpl implements TrainerFacade {

    private final TrainerService trainerService;
    private final UserDtoValidator userDtoValidator;
    private final UserNameAndPasswordChecker userNameAndPasswordChecker;

    @Override
    public TrainerDto create(TrainerDto trainerDto) {
        if(trainerDto == null) {
            LOGGER.warn(ENTITY_CANT_BE_NULL, "create");
            return null;
        }

        if(!userDtoValidator.validate(trainerDto.getUser())) {
            LOGGER.warn(userDtoValidator.getErrorMessage(trainerDto.getUser()));
            return null;
        }

        TrainerDto trainerDtoResult = trainerService.create(trainerDto);
        LOGGER.trace("create: {} was created", trainerDtoResult);
        return trainerDtoResult;
    }

    @Override
    public TrainerDto select(String userName, String password) {
        if(authenticate(userName, password)) {
            try {
                return trainerService.select(userName);
            } catch (org.gym.exception.EntityNotFoundException e) {
                LOGGER.warn(e.getMessage());
            }
        } else {
            LOGGER.warn(ACCESS_DENIED, "select", userName);
        }
        return null;
    }

    @Override
    public TrainerDto update(String userName, String password, TrainerDto trainerDto) throws EntityNotFoundException {
        if(trainerDto == null) {
            LOGGER.warn(ENTITY_CANT_BE_NULL, "create");
            return null;
        }

        if(!userDtoValidator.validate(trainerDto.getUser())) {
            LOGGER.warn(userDtoValidator.getErrorMessage(trainerDto.getUser()));
            return null;
        }

        if(!authenticate(userName, password)) {
            LOGGER.warn(ACCESS_DENIED, "update", userName);
            return null;
        }

        try {
            return trainerService.update(userName, trainerDto);
        } catch (org.gym.exception.EntityNotFoundException e) {
            LOGGER.warn(ENTITY_NOT_FOUND, "update", userName);
            return null;
        }
    }

    @Override
    public boolean authenticate(String userName, String password) {
        if (userNameAndPasswordChecker.isNullOrBlank(userName, password)) {
            LOGGER.warn(USERNAME_PASSWORD_CANT_BE_NULL_OR_BLANK, "authenticate", userName, password);
            return false;
        }

        return trainerService.authenticate(userName, password);
    }

    @Override
    public TrainerDto changeSpecialization(String userName, String password, TrainingType trainingType) {
        if(trainingType == null) {
            LOGGER.warn("changeSpecialization: trainingType can't be null");
            return null;
        }

        if(authenticate(userName, password)) {
            return trainerService.changeSpecialization(userName, trainingType);
        } else {
            LOGGER.warn(ACCESS_DENIED, "changeStatus", userName);
            return null;
        }
    }
    
    @Override
    public TrainerDto changePassword(String userName, String password, String newPassword) {
        if(userNameAndPasswordChecker.isNullOrBlank(newPassword)) {
            LOGGER.warn("{}: new password ({}) can't be null or blank", "changePassword", newPassword);
            return null;
        }

        if(authenticate(userName, password)) {
            return trainerService.changePassword(userName, newPassword);
        } else {
            LOGGER.warn(ACCESS_DENIED, "changePassword", userName);
            return null;
        }
    }

    @Override
    public List<TrainerDto> getUnassignedTrainers(String trainerUserName) {
        return trainerService.getUnassignedTrainersList(trainerUserName);
    }

    @Override
    public List<TrainerDto> updateTrainersList(String trainerUserName, List<String> trainersUserNames) {
        return trainerService.updateTrainersList(trainerUserName, trainersUserNames);
    }
}
