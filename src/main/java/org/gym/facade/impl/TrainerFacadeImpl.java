package org.gym.facade.impl;

import lombok.RequiredArgsConstructor;
import org.gym.dto.TrainerDto;
import org.gym.facade.TrainerFacade;
import org.gym.service.TrainerService;
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
    public TrainerDto getTrainerByUserName(String userName) {
        return trainerService.select(userName);
    }

    @Override
    public TrainerDto updateTrainer(String userName, TrainerDto trainerDto) {
        return trainerService.update(userName, trainerDto);
    }

    @Override
    public boolean authenticateTrainer(String userName, String password) {
        return trainerService.authenticateTrainer(userName, password);
    }

    @Override
    public void changeTrainerStatus(String userName, Boolean isActive) {
        trainerService.changeStatus(userName, isActive);
    }

    @Override
    public void changeTrainerPassword(String userName, String lastPassword, String newPassword) {
        trainerService.changePassword(userName, lastPassword, newPassword);
    }

    @Override
    public List<TrainerDto> getUnassignedTrainers(String traineeUserName) {
        return trainerService.getUnassignedTrainersList(traineeUserName);
    }

    @Override
    public List<TrainerDto> updateTrainersList(String traineeUserName, List<String> trainersUserNames) {
        return trainerService.updateTrainersList(traineeUserName, trainersUserNames);
    }
}
