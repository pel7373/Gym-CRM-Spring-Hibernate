package org.gym.facade.impl;

import org.springframework.stereotype.Component;

@Component
public class UserNameAndPasswordChecker {
    public boolean isNullOrBlank(String userName, String password) {
        return userName == null || userName.isBlank() || password == null || password.isBlank();
    }

    public boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }
}
