package org.gym.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.gym.entity.TrainingType;
import org.gym.repository.TrainingTypeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<TrainingType> findByName(String trainingTypeName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainingType> query = criteriaBuilder.createQuery(TrainingType.class);
        Root<TrainingType> root = query.from(TrainingType.class);
        query.select(root).where(criteriaBuilder.equal(
                root.get("training_type_name"), trainingTypeName));

        try {
            return Optional.of(entityManager.createQuery(query).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
