package org.gym.facade;

import org.gym.dto.TrainerDto;

import java.util.List;

public interface TrainerFacade {
    TrainerDto createTrainer(TrainerDto trainerDto);
    TrainerDto getTrainerByUsername(String username);
    TrainerDto updateTrainer(String username, TrainerDto trainerDto);
    boolean authenticateTrainer(String username, String password);
    void changeTrainerStatus(String username, Boolean isActive);
    void changeTrainerPassword(String username, String lastPassword, String newPassword);
    List<TrainerDto> getUnassignedTrainers(String traineeUsername);
    List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames);
}
