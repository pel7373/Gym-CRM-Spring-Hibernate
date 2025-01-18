package org.gym.mapper;

import org.gym.dto.UserDto;
import org.gym.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {

    private UserMapper userMapper = new UserMapperImpl();

    @Test
    void convertToDto() {
        User user = User.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .userName("FirstName.LastName")
                .password("password")
                .isActive(true)
                .build();

        UserDto userDto = userMapper.convertToDto(user);

        assertNotNull(userDto);
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getUserName(), userDto.getUserName());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getIsActive(), userDto.getIsActive());
    }

    @Test
    void convertToDtoWithNullTrainee() {
        UserDto userDto = userMapper.convertToDto(null);
        assertNull(userDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        UserDto userDto = UserDto.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .password("password")
                .userName("FirstName.LastName")
                .isActive(true)
                .build();

        User user = userMapper.convertToEntity(userDto);

        assertNotNull(user);
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getUserName(), user.getUserName());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getIsActive(), user.getIsActive());

    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        User user = userMapper.convertToEntity(null);
        assertNull(user, "Expected convertToEntity to return null when input is null");
    }
}
