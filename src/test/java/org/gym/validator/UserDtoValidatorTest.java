package org.gym.validator;

import org.gym.config.Config;
import org.gym.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
@jakarta.transaction.Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UserDtoValidatorTest {

    @Autowired
    private UserDtoValidator userDtoValidator;

    @Test
    void validateValidUserDtoSuccessfully() {
        UserDto userDto = new UserDto("Maria", "Petrenko", "Maria.Petrenko", true);
        boolean isValidUserDto = userDtoValidator.validate(userDto);
        assertTrue(isValidUserDto);
    }

    @Test
    void validateUserDtoFirstNameNull() {
        UserDto userDto = new UserDto(null, "Petrenko", "Maria.Petrenko", true);
        boolean isValidUserDto = userDtoValidator.validate(userDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoFirstNameNotValid() {
        UserDto userDto = new UserDto("Ma", "Petrenko", "Maria.Petrenko", true);
        boolean isValidUserDto = userDtoValidator.validate(userDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoLastNameNull() {
        UserDto userDto = new UserDto("Maria", null, "Maria.Petrenko", true);
        boolean isValidUserDto = userDtoValidator.validate(userDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoLastNameNotValid() {
        UserDto userDto = new UserDto("Maria", "Pe", "Maria.Petrenko", true);
        boolean isValidUserDto = userDtoValidator.validate(userDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoIsActiveNull() {
        UserDto userDto = new UserDto("Maria", "Petrenko", "Maria.Petrenko", null);
        boolean isValidUserDto = userDtoValidator.validate(userDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoIsActiveValid() {
        UserDto userDto = new UserDto("Maria", "Petrenko", "Maria.Petrenko", true);
        boolean isValidUserDto = userDtoValidator.validate(userDto);
        assertTrue(isValidUserDto);
    }
}