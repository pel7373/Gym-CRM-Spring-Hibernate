package org.gym.repository;

import jakarta.validation.Valid;
import org.gym.entity.Trainee;
import org.gym.exception.EntityNotFoundException;

import java.util.Optional;

public interface GenericRepository<T, I> {
    Optional<T> findByUserName(String userName) throws EntityNotFoundException;
    T save(@Valid T t);
    Trainee update(String UserName, T t);
}
