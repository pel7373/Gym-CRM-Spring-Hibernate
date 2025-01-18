package org.gym.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.gym.Main;
import org.gym.dto.TraineeDto;
import org.gym.dto.UserDto;
import org.gym.entity.Trainee;
import org.gym.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

class TraineeMapperTest {

//    private UserMapper userMapper = new UserMapperImpl();
//    private TraineeMapper traineeMapper = new TraineeMapperImpl();

    private static TraineeMapper traineeMapper;
    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void setUp() {
        context = new AnnotationConfigApplicationContext(Main.class);
        traineeMapper = context.getBean(TraineeMapper.class);
    }

    @Test
    void convertToDto() {
        User user = User.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .userName("FirstName.LastName")
                .password("password")
                .isActive(true).build();

        Trainee trainee = Trainee.builder()
                .user(User.builder()
                        .firstName("Fi")
                        .lastName("LastName")
                        .userName("FirstName.LastName")
                        .password("password")
                        .isActive(true).build())
                .dateOfBirth(LocalDate.of(2020, 1, 1))
                .address("123 Main St")
                .build();

        TraineeDto traineeDto = traineeMapper.convertToDto(trainee);

        assertNotNull(traineeDto);
        assertEquals(trainee.getUser().getFirstName(), traineeDto.getUser().getFirstName());
        assertEquals(trainee.getUser().getLastName(), traineeDto.getUser().getLastName());
        assertEquals(trainee.getUser().getUserName(), traineeDto.getUser().getUserName());
        assertEquals(trainee.getUser().getIsActive(), traineeDto.getUser().getIsActive());
        assertEquals(trainee.getDateOfBirth(), traineeDto.getDateOfBirth());
        assertEquals(trainee.getAddress(), traineeDto.getAddress());
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TraineeDto traineeDto = traineeMapper.convertToDto(null);
        assertNull(traineeDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        TraineeDto traineeDto = TraineeDto.builder()
                .user(UserDto.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .userName("FirstName.LastName")
                        .isActive(true)
                        .build())
                .dateOfBirth(LocalDate.of(2020, 1, 1))
                .address("123 Main St")
                .build();

        Trainee trainee = traineeMapper.convertToEntity(traineeDto);

        assertNotNull(trainee);
        assertEquals(traineeDto.getUser().getFirstName(), trainee.getUser().getFirstName());
        assertEquals(traineeDto.getUser().getLastName(), trainee.getUser().getLastName());
        assertEquals(traineeDto.getUser().getUserName(), trainee.getUser().getUserName());
        assertEquals(traineeDto.getUser().getIsActive(), trainee.getUser().getIsActive());
        assertEquals(traineeDto.getDateOfBirth(), trainee.getDateOfBirth());
        assertEquals(traineeDto.getAddress(), trainee.getAddress());
    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        Trainee trainee = traineeMapper.convertToEntity(null);
        assertNull(trainee, "Expected convertToEntity to return null when input is null");
    }
}