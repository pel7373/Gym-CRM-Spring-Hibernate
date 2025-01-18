package org.gym.service;

import org.gym.exception.EntityNotFoundException;

public interface GenericPersonService<T> {
    T select(String userName) throws EntityNotFoundException;
    T update(String userName, T t) throws EntityNotFoundException;
    void changeStatus(String userName, Boolean isActive);
    boolean authenticate(String userName, String password);
    void changePassword(String userName, String newPassword);
}
