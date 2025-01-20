package org.gym.facade.impl;

import org.springframework.stereotype.Component;

@Component
public class UserNamePasswordValidator {
    public boolean isNullOrBlank(String userName, String password) {
        return userName == null || userName.isBlank() || password == null || password.isBlank();
    }

    public boolean isNewPasswordNullOrBlank(String newPassword) {
        return newPassword == null || newPassword.isBlank();
    }
}
