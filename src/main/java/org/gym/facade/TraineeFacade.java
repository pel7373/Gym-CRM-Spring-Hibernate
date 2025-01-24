package org.gym.facade;

import org.gym.dto.TraineeDto;
import org.gym.exception.EntityNotFoundException;

public interface TraineeFacade extends GenericFacade<TraineeDto>, GenericPersonFacade<TraineeDto> {
    void delete(String userName, String password) throws EntityNotFoundException;
}
