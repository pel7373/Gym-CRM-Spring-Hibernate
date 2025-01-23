package org.gym.repository;

import org.gym.Main;
import org.gym.config.Config;
import org.gym.config.TestConfig;
import org.gym.entity.Trainee;
import org.gym.entity.User;
import org.gym.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
class TraineeRepositoryTest {

    @Autowired
    private TraineeRepository traineeRepository;

    private Trainee trainee;
    private String userNameDoesntExist = "userNameDoesntExist";

    {
        trainee = Trainee.builder()
                .user(User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .userName("John.Doe")
                        .password("password")
                        .isActive(true)
                        .build())
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("123 Main St")
                .build();
    }

    @Test
    void saveTraineeSuccessfully() {
        Trainee savedTrainee = traineeRepository.save(trainee);
        assertNotNull(savedTrainee);
        assertEquals("John", savedTrainee.getUser().getFirstName());
        assertEquals(trainee.getUser().getUserName(), savedTrainee.getUser().getUserName());
    }

    @Test
    void saveTraineeWithNullId() {
        trainee.setId(null);
        Trainee savedTrainee = traineeRepository.save(trainee);
        assertNotNull(savedTrainee);
        assertNotNull(savedTrainee.getId());
    }

    @Test
    void saveTraineeWithExistingId() {
        trainee.setId(2L);
        Trainee savedTrainee = traineeRepository.save(trainee);
        assertNotNull(savedTrainee);
        assertEquals("John", savedTrainee.getUser().getFirstName());
        assertEquals(trainee.getUser().getUserName(), savedTrainee.getUser().getUserName());
    }

    @Test
    void findByUserNameNoResult() {
        assertThrows(EntityNotFoundException.class, () ->traineeRepository.findByUserName(userNameDoesntExist));
    }

    @Test
    void findByUserNameSuccess() {
        Trainee savedTrainee = traineeRepository.save(trainee);
        Optional<Trainee> foundTrainee =
                traineeRepository.findByUserName(savedTrainee.getUser().getUserName());
        assertTrue(foundTrainee.isPresent());
        assertEquals(savedTrainee.getUser().getFirstName(),
                foundTrainee.get().getUser().getFirstName());
    }

    @Test
    void deleteByUserNameSuccess() {
        Trainee savedTrainee = traineeRepository.save(trainee);
        Optional<Trainee> foundTrainee =
                traineeRepository.findByUserName(savedTrainee.getUser().getUserName());
        assertTrue(foundTrainee.isPresent());
        traineeRepository.delete(savedTrainee.getUser().getUserName());
        assertThrows(EntityNotFoundException.class,
                () -> traineeRepository.findByUserName(savedTrainee.getUser().getUserName()));
    }

    @Test
    void deleteByUserNameDoesNotExist() {
        assertThrows(EntityNotFoundException.class,
                () ->traineeRepository.delete(userNameDoesntExist));
    }
}
