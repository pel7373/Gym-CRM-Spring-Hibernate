package org.gym.facade;

import jakarta.persistence.EntityNotFoundException;
import org.gym.dto.TraineeDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TraineeFacade extends GenericFacade<TraineeDto>, GenericPersonFacade<TraineeDto> {
    void delete(String userName, String password) throws EntityNotFoundException;
}
