package org.gym.service;

import org.gym.dto.TraineeDto;
import org.gym.exception.EntityNotFoundException;

public interface TraineeService extends GenericService<TraineeDto>, GenericPersonService<TraineeDto> {
    void delete(String userName) throws EntityNotFoundException;
}
