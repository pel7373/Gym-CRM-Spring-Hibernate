package org.gym.facade;

import jakarta.validation.Valid;
import org.gym.dto.TraineeDto;
import org.gym.exception.EntityNotFoundException;

public interface TraineeFacade {
    TraineeDto create(TraineeDto traineeDto);
    TraineeDto select(String userName, String password);
    TraineeDto update(String userName, String password, TraineeDto TraineeDto);
    TraineeDto changeStatus(String userName, String password, Boolean isActive);
    boolean authenticate(String userName, String password);
    TraineeDto changePassword(String userName, String password, String newPassword);
    void delete(String userName, String password);
}
