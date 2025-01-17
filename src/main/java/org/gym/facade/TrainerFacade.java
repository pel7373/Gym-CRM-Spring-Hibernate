package org.gym.facade;

import org.gym.dto.TrainerDto;

import java.util.List;

public interface TrainerFacade {
    TrainerDto createTrainer(TrainerDto trainerDto);
    TrainerDto getTrainerByUserName(String userName);
    TrainerDto updateTrainer(String userName, TrainerDto trainerDto);
    boolean authenticateTrainer(String userName, String password);
    void changeTrainerStatus(String userName, Boolean isActive);
    void changeTrainerPassword(String userName, String lastPassword, String newPassword);
    List<TrainerDto> getUnassignedTrainers(String traineeUserName);
    List<TrainerDto> updateTrainersList(String traineeUserName, List<String> trainersUserNames);
}
