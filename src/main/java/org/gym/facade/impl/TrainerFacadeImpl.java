package org.gym.facade.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.gym.dto.TraineeDto;
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
    public TrainerDto create(TrainerDto trainerDto) {
        return trainerService.create(trainerDto);
    }

    @Override
    public TrainerDto select(String userName, String password) {
        return trainerService.select(userName);
    }

    @Override
    public TrainerDto update(String userName, String password, TrainerDto trainerDto) throws EntityNotFoundException {
        return trainerService.update(userName, trainerDto);
    }


    @Override
    public boolean authenticate(String userName, String password) {
        return trainerService.authenticate(userName, password);
    }


    @Override
    public void changeStatus(String userName, String password, Boolean isActive) {
        trainerService.changeStatus(userName, isActive);
    }

    @Override
    public void changePassword(String userName, String password, String newPassword) {
       // trainerService.changePassword(userName, newPassword);
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
