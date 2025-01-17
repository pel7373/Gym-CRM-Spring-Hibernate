package org.gym.service;

import jakarta.validation.Valid;
import org.gym.dto.TrainerDto;
import org.gym.exception.EntityNotFoundException;

import java.util.List;

public interface TrainerService {
    TrainerDto create(@Valid TrainerDto trainerDTO);
    TrainerDto select(String username) throws EntityNotFoundException;
    TrainerDto update(String username, @Valid TrainerDto trainerDto) throws EntityNotFoundException;
    boolean authenticateTrainer(String username, String password);
    void changeStatus(String username, Boolean isActive);
    void changePassword(String username, String lastPassword, String newPassword);
    List<TrainerDto> getUnassignedTrainersList(String traineeUsername);
    List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames);
}
