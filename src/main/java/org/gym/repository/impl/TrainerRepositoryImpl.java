package org.gym.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.gym.entity.Trainee;
import org.gym.repository.TrainerRepository;
import org.gym.entity.Trainer;
import org.gym.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Trainer> findByUserName(String userName) throws EntityNotFoundException {
        return null;
    }

    @Transactional
    public Trainer save(Trainer trainer) {
        if (trainer == null || trainer.getUser() == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        if (trainer.getUser().getUserName() == null || trainer.getUser().getUserName().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        entityManager.merge(trainer);

//        if (trainee.getId() == null) {
//            entityManager.persist(trainee);
//        } else {
//            trainee = entityManager.merge(trainee);
//        }
        return trainer;
    }

    @Override
    public Trainee update(String UserName, Trainer trainer) {
        return null;
    }
}
