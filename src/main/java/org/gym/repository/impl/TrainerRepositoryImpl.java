package org.gym.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.gym.entity.Trainee;
import org.gym.repository.TrainerRepository;
import org.gym.entity.Trainer;
import org.gym.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.gym.config.AppConfig.ENTITY_NOT_FOUND_EXCEPTION;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Trainer> findByUserName(String userName) throws EntityNotFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> query = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = query.from(Trainer.class);
        query.select(root).where(criteriaBuilder.equal(root.get("user").get("userName"), userName));

        try {
            return Optional.of(entityManager.createQuery(query).getSingleResult());
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_EXCEPTION, "findByUserName", userName));
        }
    }

    @Transactional
    public Trainer save(Trainer trainer) {
        Trainer savedTrainer = trainer;
        if (trainer.getId() == null) {
            entityManager.persist(trainer);
        } else {
            savedTrainer = entityManager.merge(trainer);
        }
        return savedTrainer;
    }

    @Override
    public List<Trainer> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
