package org.gym.facade.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.dto.TraineeDto;
import org.gym.dto.UserDto;
import org.gym.dto.validator.UserDtoValidator;
import org.gym.exception.EntityNotFoundException;
import org.gym.facade.TraineeFacade;
import org.gym.service.TraineeService;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

import static org.gym.config.AppConfig.ACCESS_DENIED;

@Slf4j
@Component
@RequiredArgsConstructor
@Validated
public class TraineeFacadeImpl implements TraineeFacade {

    private final TraineeService traineeService;
    private final UserDtoValidator userDtoValidator;

    @Override
    public TraineeDto create(@Valid TraineeDto traineeDto) {
        if(traineeDto == null) {
            LOGGER.warn("create: traineeDto can't be null");
            return null;
        }

        if(!userDtoValidator.validate(traineeDto.getUser())) {
            LOGGER.warn(userDtoValidator.getErrorMessage(traineeDto.getUser()));
            return null;
        }

        TraineeDto traineeDtoResult = traineeService.create(traineeDto);
        LOGGER.trace("create: {} was created", traineeDtoResult);
        return traineeDtoResult;
    }

    @Override
    public TraineeDto select(String userName, String password) {
        if(authenticate(userName, password)) {
            try {
                return traineeService.select(userName);
            } catch (EntityNotFoundException e) {
                LOGGER.warn(e.getMessage());
            }
        } else {
            LOGGER.warn(ACCESS_DENIED, "select", userName);
        }
        return null;
    }

    @Override
    public TraineeDto update(String userName, String password, TraineeDto traineeDto) {
        if(traineeDto == null) {
            LOGGER.warn("update: traineeDto can't be null");
            return null;
        }

        if(authenticate(userName, password)) {
            return traineeService.update(userName, traineeDto);
        } else {
            LOGGER.warn(ACCESS_DENIED, "update", userName);
            return null;
        }
    }

    @Override
    public void delete(String userName, String password) {
        if(userName == null || userName.isBlank()) {
            LOGGER.warn("delete: userName can't be null");
            return;
        }

        if(authenticate(userName, password)) {
            traineeService.delete(userName);
        } else {
            LOGGER.warn(ACCESS_DENIED, "delete", userName);
        }
    }

    @Override
    public void changeStatus(String userName, String password, Boolean isActive) {
        if(isActive == null) {
            LOGGER.warn("delete: userName can't be null");
            return;
        }

        if(authenticate(userName, password)) {
            traineeService.changeStatus(userName, isActive);
        } else {
            LOGGER.warn(ACCESS_DENIED, "changeStatus", userName);
        }
    }

    @Override
    public boolean authenticate(String userName, String password) {
        if(userName == null || userName.isBlank()
                || password == null || password.isBlank()) {
            LOGGER.warn("authenticate: userName or/and password can't be null or blank");
            return false;
        }
        return traineeService.authenticate(userName, password);
    }

    @Override
    public void changePassword(String userName, String password, String newPassword) {
        if(userName == null || userName.isBlank()
                || password == null || password.isBlank()
                || newPassword == null || newPassword.isBlank()) {
            LOGGER.warn("authenticate: userName or/and password can't be null or blank");
            return;
        }

        if(authenticate(userName, password)) {
            traineeService.changePassword(userName, newPassword);
        } else {
            LOGGER.warn(ACCESS_DENIED, "changePassword", userName);
        }
    }
}
