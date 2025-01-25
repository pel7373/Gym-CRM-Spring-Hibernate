package org.gym.validator;

import org.gym.config.Config;
import org.gym.dto.*;
import org.gym.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
@jakarta.transaction.Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class TrainingDtoValidatorTest {

    @Autowired
    private TrainingDtoValidator trainingDtoValidator;

    private TrainingDto trainingDto;
    private TrainingTypeDto trainingTypeDto;
    private TrainerDto trainerDto;
    private TraineeDto traineeDto;
    private String trainingTypeName;

    @BeforeEach
    void setUp() {
        trainingTypeName = "Zumba";
        trainingTypeDto = TrainingTypeDto.builder()
                .trainingTypeName(trainingTypeName)
                .build();

        traineeDto = TraineeDto.builder()
                .user(new UserDto("Maria", "Petrenko", "Maria.Petrenko", true))
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        trainerDto = TrainerDto.builder()
                .user(new UserDto("Petro", "Ivanenko", "Petro.Ivanenko", true))
                .specialization(TrainingTypeDto.builder()
                        .trainingTypeName("Zumba")
                        .build())
                .build();

        trainingDto = TrainingDto.builder()
                .trainingType(trainingTypeDto)
                .trainer(trainerDto)
                .trainee(traineeDto)
                .trainingName("Zumba next workout")
                .trainingType(trainingTypeDto)
                .date(LocalDate.now().plusDays(3))
                .duration(45)
                .build();
    }

    @Test
    void validateValidSuccessfully() {
        boolean isValidUserDto = trainingDtoValidator.validate(trainingDto);
        assertTrue(isValidUserDto);
    }

    @Test
    void validateUserDtoFirstNameNull() {

        boolean isValidUserDto = trainingDtoValidator.validate(trainingDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoFirstNameNotValid() {

        boolean isValidUserDto = trainingDtoValidator.validate(trainingDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoLastNameNull() {

        boolean isValidUserDto = trainingDtoValidator.validate(trainingDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoLastNameNotValid() {

        boolean isValidUserDto = trainingDtoValidator.validate(trainingDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoIsActiveNull() {

        boolean isValidUserDto = trainingDtoValidator.validate(trainingDto);
        assertFalse(isValidUserDto);
    }

    @Test
    void validateUserDtoIsActiveValid() {

        boolean isValidUserDto = trainingDtoValidator.validate(trainingDto);
        assertTrue(isValidUserDto);
    }

}