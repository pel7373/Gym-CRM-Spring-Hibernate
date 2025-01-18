package org.gym.repository;

import org.gym.entity.Trainee;
import org.gym.exception.EntityNotFoundException;

import java.util.Optional;

public interface GenericPersonRepository<T, I> {
    Optional<T> findByUserName(String userName) throws EntityNotFoundException;
    T save(T t);
}
