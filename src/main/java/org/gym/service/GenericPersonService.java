package org.gym.service;

import org.gym.exception.EntityNotFoundException;

public interface GenericPersonService<T> {
    T select(String userName) throws EntityNotFoundException;
    T update(String userName, T t) throws EntityNotFoundException;
    boolean authenticate(String userName, String password);
    T changePassword(String userName, String newPassword);
    String getPasswordTopSecretMethod(String userName);
}
