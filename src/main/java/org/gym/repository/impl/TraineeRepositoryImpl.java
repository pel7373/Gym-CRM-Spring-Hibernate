package org.gym.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.gym.exception.EntityNotFoundException;
import org.gym.repository.TraineeRepository;
import org.gym.entity.Trainee;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.gym.config.Config.ENTITY_NOT_FOUND_EXCEPTION;

@Repository
@RequiredArgsConstructor
public class TraineeRepositoryImpl implements TraineeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Trainee> findByUserName(String userName) throws EntityNotFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainee> query = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = query.from(Trainee.class);
        query.select(root).where(criteriaBuilder.equal(root.get("user").get("userName"), userName));

        try {
            return Optional.of(entityManager.createQuery(query).getSingleResult());
        } catch (NoSuchElementException | NoResultException e) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_EXCEPTION, userName));
        }
    }

    @Override
    public Trainee save(Trainee trainee) {
        Trainee savedTrainee = trainee;
        if (trainee.getId() == null) {
            entityManager.persist(trainee);
        } else {
            savedTrainee = entityManager.merge(trainee);
        }
        return savedTrainee;
    }

    @Override
    public void delete(String userName) throws EntityNotFoundException {
        findByUserName(userName).ifPresent(entityManager::remove);
    }
}
