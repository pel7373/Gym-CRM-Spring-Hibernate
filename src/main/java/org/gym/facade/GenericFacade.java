package org.gym.facade;

import jakarta.validation.Valid;

public interface GenericFacade<T> {
    T create(@Valid T t);
}
