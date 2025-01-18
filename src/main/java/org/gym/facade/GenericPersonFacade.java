package org.gym.facade;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

public interface GenericPersonFacade<T> {
    T select(String userName, String password) throws EntityNotFoundException;
    T update(String userName, String password, @Valid T t) throws EntityNotFoundException;
    void changeStatus(String userName, String password, Boolean isActive);
    boolean authenticate(String userName, String password);
    void changePassword(String userName, String password, String newPassword);

}
