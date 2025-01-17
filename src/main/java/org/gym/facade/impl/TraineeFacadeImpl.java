package org.gym.facade.impl;

import lombok.RequiredArgsConstructor;
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
    public TraineeDto getTraineeByUsername(String username) {
        return traineeService.select(username);
    }

    @Override
    public TraineeDto updateTrainee(String username, TraineeDto traineeDto) {
        return traineeService.update(username, traineeDto);
    }

    @Override
    public void deleteTrainee(String username) {
        traineeService.delete(username);
    }

    @Override
    public void changeTraineeStatus(String username, Boolean isActive) {
        traineeService.changeStatus(username, isActive);
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
