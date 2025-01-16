package org.gym.repository;

import org.gym.entity.Trainer;

import java.util.List;

public interface TrainerRepository extends GenericRepository<Trainer, Long> {
    List<Trainer> findAll();
    Trainer update(Long id, Trainer t);
    boolean isUserNameExists(String userName);
}
