package org.gym.repository;

import org.gym.entity.Trainee;

import java.util.List;

public interface TraineeRepository extends GenericRepository<Trainee, Long> {
    List<Trainee> findAll();
    Trainee update(Long id, Trainee t);
    void deleteByUserName(String userName);
}
