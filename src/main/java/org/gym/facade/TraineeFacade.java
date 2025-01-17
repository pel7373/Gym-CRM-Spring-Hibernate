package org.gym.facade;

import jakarta.persistence.EntityNotFoundException;
import org.gym.dto.TraineeDto;

public interface TraineeFacade {
    TraineeDto createTrainee(TraineeDto traineeDto);
    TraineeDto getTraineeByUserName(String userName) throws EntityNotFoundException;
    TraineeDto updateTrainee(String userName, TraineeDto traineeDto) throws EntityNotFoundException;
    void deleteTrainee(String userName) throws EntityNotFoundException;
    void changeTraineeStatus(String userName, Boolean isActive);
    boolean authenticateTrainee(String userName, String password);
    void changeTraineePassword(String userName, String lastPassword, String newPassword);
}
