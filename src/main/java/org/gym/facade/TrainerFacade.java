package org.gym.facade;

import org.gym.dto.TraineeDto;
import org.gym.dto.TrainerDto;

import java.util.List;

public interface TrainerFacade extends GenericFacade<TrainerDto>, GenericPersonFacade<TrainerDto> {
    List<TrainerDto> getUnassignedTrainers(String traineeUserName);
    List<TrainerDto> updateTrainersList(String traineeUserName, List<String> trainersUserNames);
}
