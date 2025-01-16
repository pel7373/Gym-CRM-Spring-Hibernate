package org.gym.repository;

import org.gym.exception.EntityNotFoundException;

public interface GenericRepository<T, I> {
    T findById(I id) throws EntityNotFoundException;
    T save(T t);
    //saveOrUpdate
}
