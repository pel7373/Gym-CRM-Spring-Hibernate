package org.gym.facade;

import org.gym.dto.TrainerDto;
import org.gym.entity.TrainingType;

import java.util.List;

public interface TrainerFacade extends GenericFacade<TrainerDto>, GenericPersonFacade<TrainerDto> {
    TrainerDto changeSpecialization(String userName, String password, TrainingType trainingType);
    List<TrainerDto> getUnassignedTrainers(String traineeUserName);
    List<TrainerDto> updateTrainersList(String traineeUserName, List<String> trainersUserNames);
}
