package org.gym.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.gym.repository.TrainerRepository;
import org.gym.entity.Trainer;
import org.gym.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainerRepositoryImpl implements TrainerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Trainer> findAll() {
        return null;// trainerStorage.findAll();
    }

    @Override
    public Trainer findById(Long id) throws EntityNotFoundException {
        return new Trainer();// trainerStorage.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
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
    public Trainer update(Long id, Trainer trainer) {
        return trainer;// trainerStorage.update(id, trainer);
    }

    @Override
    public boolean isUserNameExists(String userName) {
        return false;// trainerStorage.isUserNameExist(userName);
    }

//    private Session getSession() {
//        return sessionFactory.getCurrentSession();
//    }
}
