package org.gym.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.gym.dto.TraineeDto;

public interface TraineeService {
    TraineeDto create(@Valid TraineeDto traineeDTO);
    TraineeDto select(String userName) throws EntityNotFoundException;
    TraineeDto update(String userName, @Valid TraineeDto traineeDTO) throws EntityNotFoundException;
    void delete(String userName) throws EntityNotFoundException;
    void changeStatus(String userName, Boolean isActive);
    boolean authenticateTrainee(String userName, String password) throws EntityNotFoundException;
    void changePassword(String userName, String oldPassword, String newPassword) throws EntityNotFoundException, IllegalArgumentException;
}
