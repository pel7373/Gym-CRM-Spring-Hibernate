package org.gym.facade;

import jakarta.validation.Valid;
import org.gym.exception.EntityNotFoundException;

public interface GenericPersonFacade<T> {
    T select(String userName, String password) throws EntityNotFoundException;
    T update(String userName, String password, @Valid T t) throws EntityNotFoundException;
    T changeStatus(String userName, String password, Boolean isActive);
    boolean authenticate(String userName, String password);
    T changePassword(String userName, String password, String newPassword);

}
