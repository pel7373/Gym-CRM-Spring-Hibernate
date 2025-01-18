package org.gym.facade;

import org.gym.Main;
import org.gym.config.TestConfig;
import org.gym.dto.UserDto;
import org.gym.entity.Trainee;
import org.gym.dto.TraineeDto;
import org.gym.exception.EntityNotFoundException;
import org.gym.facade.impl.TraineeFacadeImpl;
import org.gym.repository.TraineeRepository;
import org.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {Main.class, TestConfig.class})
class TraineeFacadeImplIT {

    @Autowired
    private TraineeFacadeImpl traineeFacade;

    @Autowired
    private TraineeRepository traineeRepository;

    private TraineeDto traineeDto;
    private TraineeDto traineeDto2;
    private String userNameForTrainee;

    @BeforeEach
    void setUp() {
        UserDto userDto = new UserDto("Maria", "Petrenko", "Maria.Petrenko", true);
        UserDto userDto2 = new UserDto("Pa", "Pa", "Maria.Petrenko2", false);

        traineeDto = TraineeDto.builder()
                .user(userDto)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        TraineeDto traineeDto2
                = new TraineeDto(userDto2, LocalDate.of(1995, 1, 23),
                "Kyiv, Soborna str. 35, ap. 26");
    }

    @Test
    void saveTraineeToStorageSuccessfully() {
        TraineeDto createdTraineeDto = traineeFacade.create(traineeDto);
        userNameForTrainee = createdTraineeDto.getUser().getUserName();

        assertNotNull(createdTraineeDto);
        assertNotNull(createdTraineeDto.getUser());
        assertAll(
                "Grouped assertions of created traineeDto",
                () -> assertEquals("Maria", createdTraineeDto.getUser().getFirstName(), "firstName should be Maria"),
                () -> assertEquals("Petrenko", createdTraineeDto.getUser().getLastName(), "lastName should be Petrenko"),
                () -> assertTrue(createdTraineeDto.getUser().getIsActive(), "isActive should be true"),
                () -> assertEquals("Vinnitsya, Soborna str. 35, ap. 26", createdTraineeDto.getAddress(), "address should be Vinnitsya, Soborna str. 35, ap. 26")
        );

        Trainee createdTrainee = traineeRepository.findByUserName(createdTraineeDto.getUser().getUserName()).get();
        assertNotNull(createdTrainee);
        assertNotNull(createdTrainee.getUser());
        assertAll(
                "Grouped assertions of created trainee",
                () -> assertEquals("Maria", createdTrainee.getUser().getFirstName(), "firstName should be Maria"),
                () -> assertEquals("Petrenko", createdTrainee.getUser().getLastName(), "lastName should be Petrenko"),
                () -> assertTrue(createdTrainee.getUser().getIsActive(), "isActive should be true"),
                () -> assertEquals("Vinnitsya, Soborna str. 35, ap. 26", createdTrainee.getAddress(), "address should be Vinnitsya, Soborna str. 35, ap. 26")
        );


    }

    @Test
    void select() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void authenticate() {
    }

    @Test
    void changePassword() {
    }
}