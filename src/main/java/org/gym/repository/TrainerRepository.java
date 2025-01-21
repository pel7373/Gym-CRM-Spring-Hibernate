package org.gym.repository;

import org.gym.entity.Trainer;

import java.util.List;

public interface TrainerRepository extends GenericPersonRepository<Trainer, Long> {
    List<Trainer> findAll();
}
