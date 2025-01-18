package org.gym.service;

import jakarta.validation.Valid;
import org.gym.dto.TrainerDto;
import org.gym.exception.EntityNotFoundException;
import org.gym.exception.NullEntityException;

import java.util.List;

public interface TrainerService extends GenericService<TrainerDto>, GenericPersonService<TrainerDto> {
    List<TrainerDto> getUnassignedTrainersList(String traineeUserName);
    List<TrainerDto> updateTrainersList(String traineeUserName, List<String> trainersUsernames);
}
