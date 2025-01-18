package org.gym.facade;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

public interface GenericFacade<T> {
    T create(@Valid T t);
}
