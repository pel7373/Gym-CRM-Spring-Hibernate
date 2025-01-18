package org.gym.facade.impl;

public class UserNamePasswordValidator {
    boolean isNullOrBlank(String userName, String password) {
        return userName == null || userName.isBlank() || password == null || password.isBlank();
    }

    boolean isNewPasswordNullOrBlank(String newPassword) {
        return newPassword == null || newPassword.isBlank();
    }
}
