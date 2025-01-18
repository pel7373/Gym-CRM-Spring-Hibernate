package org.gym.repository;

import org.gym.entity.Trainee;
import org.gym.exception.EntityNotFoundException;

public interface TraineeRepository extends GenericPersonRepository<Trainee, Long> {
    void delete(String userName) throws EntityNotFoundException;
}
