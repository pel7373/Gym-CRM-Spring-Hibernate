package org.gym.service;

import jakarta.validation.Valid;
import org.gym.dto.TraineeDto;
import org.gym.dto.TrainerDto;
import org.gym.entity.TrainingType;
import org.gym.exception.EntityNotFoundException;
import org.gym.exception.NullEntityException;

import java.util.List;

public interface TrainerService extends GenericService<TrainerDto>, GenericPersonService<TrainerDto> {
    TrainerDto changeSpecialization(String userName, TrainingType trainingType);
    List<TrainerDto> getUnassignedTrainersList(String traineeUsername) throws EntityNotFoundException;
    List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames) throws EntityNotFoundException;
}
