package org.gym.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.gym.repository.TrainingRepository;
import org.gym.entity.Training;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrainingRepositoryImpl implements TrainingRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Training save(Training training) {
        Training savedTraining = training;
        if (training.getId() == null) {
            entityManager.persist(training);
        } else {
            savedTraining = entityManager.merge(training);
        }
        return savedTraining;
    }

    @Override
    public List<Training> getByTraineeCriteria(String traineeUsername,
                                               LocalDate fromDate, LocalDate toDate,
                                               String trainerName, String trainingType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = query.from(Training.class);

        Predicate[] predicates = new Predicate[5];
        predicates[0] = criteriaBuilder.equal(root.get("trainee").get("user").get("userName"), traineeUsername);
        predicates[1] = criteriaBuilder.greaterThanOrEqualTo(root.get("date"), fromDate);
        predicates[2] = criteriaBuilder.lessThanOrEqualTo(root.get("date"), toDate);
        predicates[3] = criteriaBuilder.equal(root.get("trainer").get("user").get("firstName"), trainerName);
        predicates[4] = criteriaBuilder.equal(root.get("trainingType").get("trainingTypeName"), trainingType);

        query.select(root).where(predicates);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Training> getByTrainerCriteria(String trainerUsername,
                                               LocalDate fromDate, LocalDate toDate,
                                               String traineeName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = query.from(Training.class);

        Predicate[] predicates = new Predicate[4];
        predicates[0] = criteriaBuilder.equal(root.get("trainer").get("user").get("username"), trainerUsername);
        predicates[1] = criteriaBuilder.greaterThanOrEqualTo(root.get("date"), fromDate);
        predicates[2] = criteriaBuilder.lessThanOrEqualTo(root.get("date"), toDate);
        predicates[3] = criteriaBuilder.equal(root.get("trainee").get("user").get("firstName"), traineeName);

        query.select(root).where(predicates);
        return entityManager.createQuery(query).getResultList();
    }
}
