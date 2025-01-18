package org.gym.service;

import jakarta.validation.Valid;
import org.gym.dto.TrainerDto;
import org.gym.exception.EntityNotFoundException;
import org.gym.exception.NullEntityException;

import java.util.List;

public interface TrainerService {
    TrainerDto create(TrainerDto trainerDTO) throws NullEntityException;
    TrainerDto select(String username) throws EntityNotFoundException;
    TrainerDto update(String username, @Valid TrainerDto trainerDto) throws EntityNotFoundException;
    boolean authenticate(String username, String password);
    void changeStatus(String username, Boolean isActive);
    void changePassword(String username, String password, String newPassword);
    List<TrainerDto> getUnassignedTrainersList(String traineeUsername);
    List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames);
}
