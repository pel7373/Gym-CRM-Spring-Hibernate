package org.gym.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import org.gym.exception.EntityNotFoundException;
import org.gym.exception.NullEntityException;
import org.gym.repository.TraineeRepository;
import org.gym.entity.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Repository
public class TraineeRepositoryImpl implements TraineeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Trainee> findByUserName(String userName) throws EntityNotFoundException {
        if (userName == null) {
            throw new NullEntityException("findByUserName: userName cannot be null");
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainee> query = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = query.from(Trainee.class);
        query.select(root).where(criteriaBuilder.equal(root.get("user").get("userName"), userName));

        try {
            return Optional.of(entityManager.createQuery(query).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Trainee save(@Valid Trainee trainee) {
        if (trainee == null || trainee.getUser() == null) {
            throw new NullEntityException("Entity cannot be null");
        }
        if (trainee.getUser().getUserName() == null || trainee.getUser().getUserName().isEmpty()) {
            throw new IllegalArgumentException("UserName cannot be null or empty");
        }

        Trainee savedTrainee = trainee;
        if (trainee.getId() == null) {
            //System.out.println("Repo save persist: " + trainee);
            entityManager.persist(trainee);
        } else {
            //System.out.println("Repo save merge: " + trainee);
            savedTrainee = entityManager.merge(trainee);
        }
        //System.out.println("Repo save saved: " + savedTrainee);
        return savedTrainee;
    }

    @Override
    public Trainee update(String userName, Trainee t) {
        return null;
    }

    @Override
    public void delete(String userName) {

    }
}
