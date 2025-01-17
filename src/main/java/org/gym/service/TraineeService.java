package org.gym.service;

import jakarta.validation.Valid;
import org.gym.dto.TraineeDto;
import org.gym.exception.EntityNotFoundException;

public interface TraineeService {
    TraineeDto create(@Valid TraineeDto traineeDTO);
    TraineeDto select(String username) throws EntityNotFoundException;
    TraineeDto update(String username, @Valid TraineeDto traineeDTO) throws EntityNotFoundException;
    void delete(String username) throws EntityNotFoundException;
    void changeStatus(String username, Boolean isActive);
    boolean authenticateTrainee(String username, String password) throws EntityNotFoundException;
    void changePassword(String username, String lastPassword, String newPassword) throws EntityNotFoundException, IllegalArgumentException;
}
