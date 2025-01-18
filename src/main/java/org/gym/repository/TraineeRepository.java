package org.gym.repository;

import org.gym.entity.Trainee;

import java.util.List;

public interface TraineeRepository extends GenericRepository<Trainee, Long> {
    void delete(String userName);
}
