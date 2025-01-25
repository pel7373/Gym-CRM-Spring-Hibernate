package org.gym.facade;

import org.gym.facade.impl.UserNameAndPasswordChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserNameAndPasswordCheckerTest {

    @InjectMocks
    private UserNameAndPasswordChecker userNameAndPasswordChecker;

    @Test
    void isNullOrBlankUserNameAndPasswordNotNullOrBlank() {
        String userName = "aaa";
        String password = "bbb";
        boolean result = userNameAndPasswordChecker.isNullOrBlank(userName, password);
        assertFalse(result);
    }

    @Test
    void isNullOrBlankPasswordBlank() {
        String userName = "aaa";
        String password = "";
        boolean result = userNameAndPasswordChecker.isNullOrBlank(userName, password);
        assertTrue(result);
    }

    @Test
    void isNullOrBlankPasswordNull() {
        String userName = "aaa";
        String password = null;
        boolean result = userNameAndPasswordChecker.isNullOrBlank(userName, password);
        assertTrue(result);
    }

    @Test
    void isNullOrBlankUserNameAndPasswordNull() {
        String userName = null;
        String password = null;
        boolean result = userNameAndPasswordChecker.isNullOrBlank(userName, password);
        assertTrue(result);
    }

    @Test
    void isNullOrBlankUserNameNull() {
        String userName = null;
        boolean result = userNameAndPasswordChecker.isNullOrBlank(userName);
        assertTrue(result);
    }

    @Test
    void isNullOrBlankUserNameBlank() {
        String userName = "  ";
        boolean result = userNameAndPasswordChecker.isNullOrBlank(userName);
        assertTrue(result);
    }

    @Test
    void isNullOrBlankUserNameNotNullOrBlank() {
        String userName = "aaaa";
        boolean result = userNameAndPasswordChecker.isNullOrBlank(userName);
        assertFalse(result);
    }
}
