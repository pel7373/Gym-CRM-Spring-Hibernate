package org.gym.facade.impl;

import lombok.RequiredArgsConstructor;
import org.gym.dto.TraineeDto;
import org.gym.facade.TraineeFacade;
import org.gym.service.TraineeService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraineeFacadeImpl implements TraineeFacade {

    private final TraineeService traineeService;

    @Override
    public TraineeDto createTrainee(TraineeDto traineeDto) {
        return traineeService.create(traineeDto);
    }

    @Override
    public TraineeDto getTraineeByUserName(String userName) {
        return traineeService.select(userName);
    }

    @Override
    public TraineeDto updateTrainee(String username, TraineeDto traineeDto) {
        return traineeService.update(username, traineeDto);
    }

    @Override
    public void deleteTrainee(String userName) {
        traineeService.delete(userName);
    }

    @Override
    public void changeTraineeStatus(String userName, Boolean isActive) {
        traineeService.changeStatus(userName, isActive);
    }

    @Override
    public boolean authenticateTrainee(String username, String password) {
        return traineeService.authenticateTrainee(username, password);
    }

    @Override
    public void changeTraineePassword(String username, String lastPassword, String newPassword) {
        traineeService.changePassword(username, lastPassword, newPassword);
    }
}
