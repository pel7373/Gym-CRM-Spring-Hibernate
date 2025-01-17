package org.gym.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainerFacadeImpl implements TrainerFacade {

    private final TrainerService trainerService;

    @Override
    public TrainerDto createTrainer(TrainerDto trainerDto) {
        return trainerService.create(trainerDto);
    }

    @Override
    public TrainerDto getTrainerByUsername(String username) {
        return trainerService.select(username);
    }

    @Override
    public TrainerDto updateTrainer(String username, TrainerDto trainerDto) {
        return trainerService.update(username, trainerDto);
    }

    @Override
    public boolean authenticateTrainer(String username, String password) {
        return trainerService.authenticateTrainer(username, password);
    }

    @Override
    public void changeTrainerStatus(String username, Boolean isActive) {
        trainerService.changeStatus(username, isActive);
    }

    @Override
    public void changeTrainerPassword(String username, String lastPassword, String newPassword) {
        trainerService.changePassword(username, lastPassword, newPassword);
    }

    @Override
    public List<TrainerDto> getUnassignedTrainers(String traineeUsername) {
        return trainerService.getUnassignedTrainersList(traineeUsername);
    }

    @Override
    public List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames) {
        return trainerService.updateTrainersList(traineeUsername, trainersUsernames);
    }
}
