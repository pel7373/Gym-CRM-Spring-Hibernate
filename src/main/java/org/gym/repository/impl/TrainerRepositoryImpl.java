package org.gym.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.gym.repository.TrainerRepository;
import org.gym.entity.Trainer;
import org.gym.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.gym.config.Config.ENTITY_NOT_FOUND_EXCEPTION;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Trainer> findByUserName(String userName) throws EntityNotFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user").get("userName"), userName));

        try {
            return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (NoSuchElementException | NoResultException e) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_EXCEPTION, "findByUserName", userName));
        }
    }

    @Override
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
