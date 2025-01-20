package org.gym.mapper;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TraineeMapperTest {

    private static TraineeMapper traineeMapper;
    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void setUp() {
        context = new AnnotationConfigApplicationContext(Main.class);
        traineeMapper = context.getBean(TraineeMapper.class);
    }

    @Test
    void convertToDto() {
        User user = new User(null, "Maria", "Petrenko", "Maria.Petrenko", "", true);

        Trainee trainee = Trainee.builder()
                .user(user)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        TraineeDto createdTraineeDto = traineeMapper.convertToDto(trainee);

        assertNotNull(createdTraineeDto);
        assertAll(
                "Grouped assertions of created traineeDto",
                () -> assertEquals(trainee.getUser().getFirstName(), createdTraineeDto.getUser().getFirstName(), "check firstName"),
                () -> assertEquals(trainee.getUser().getLastName(), createdTraineeDto.getUser().getLastName(), "check lastName"),
                () -> assertEquals(trainee.getUser().getUserName(), createdTraineeDto.getUser().getUserName(), "check userName"),
                () -> assertEquals(trainee.getUser().getIsActive(), createdTraineeDto.getUser().getIsActive(), "check isActive"),
                () -> assertEquals(trainee.getDateOfBirth(), createdTraineeDto.getDateOfBirth(), "check date of birth"),
                () -> assertEquals(trainee.getAddress(), createdTraineeDto.getAddress(), "check address")
        );
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TraineeDto traineeDto = traineeMapper.convertToDto(null);
        assertNull(traineeDto, "ConvertToDto: expect null when input is null");
    }

    @Test
    void convertToEntity() {
        UserDto userDto = new UserDto("Maria", "Petrenko", "Maria.Petrenko", true);

        TraineeDto traineeDto = TraineeDto.builder()
                .user(userDto)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        Trainee createdTrainee = traineeMapper.convertToEntity(traineeDto);

        assertNotNull(createdTrainee);
        assertAll(
                "Grouped assertions of created traineeDto",
                () -> assertEquals(traineeDto.getUser().getFirstName(), createdTrainee.getUser().getFirstName(), "check firstName"),
                () -> assertEquals(traineeDto.getUser().getLastName(), createdTrainee.getUser().getLastName(), "check lastName"),
                () -> assertEquals(traineeDto.getUser().getUserName(), createdTrainee.getUser().getUserName(), "check userName"),
                () -> assertEquals(traineeDto.getUser().getIsActive(), createdTrainee.getUser().getIsActive(), "check isActive"),
                () -> assertEquals(traineeDto.getDateOfBirth(), createdTrainee.getDateOfBirth(), "check date of birth"),
                () -> assertEquals(traineeDto.getAddress(), createdTrainee.getAddress(), "check address")
        );
    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        Trainee trainee = traineeMapper.convertToEntity(null);
        assertNull(trainee, "ConvertToEntity: expect null when input is null");
    }
}
