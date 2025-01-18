package org.gym.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.gym.dto.TraineeDto;
import org.gym.exception.NullEntityException;
import org.springframework.validation.annotation.Validated;

public interface TraineeService {
    TraineeDto create(@Valid TraineeDto traineeDto) throws NullEntityException;
    TraineeDto select(String userName) throws EntityNotFoundException;
    TraineeDto update(String userName, TraineeDto traineeDto) throws EntityNotFoundException;
    void delete(String userName) throws EntityNotFoundException;
    void changeStatus(String userName, Boolean isActive);
    boolean authenticate(String userName, String password) throws EntityNotFoundException;
    void changePassword(String userName, String newPassword) throws EntityNotFoundException, IllegalArgumentException;
}
