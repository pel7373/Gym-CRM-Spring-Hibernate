package org.gym.service;

import org.gym.dto.TrainerDto;
import org.gym.entity.TrainingType;
import org.gym.exception.EntityNotFoundException;

import java.util.List;

public interface TrainerService extends GenericService<TrainerDto>, GenericPersonService<TrainerDto> {
    TrainerDto changeSpecialization(String userName, TrainingType trainingType) throws EntityNotFoundException;
    List<TrainerDto> getUnassignedTrainersList(String traineeUsername) throws EntityNotFoundException;
    List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames) throws EntityNotFoundException;
}
