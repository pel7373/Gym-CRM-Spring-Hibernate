package org.gym.service;

import jakarta.transaction.Transactional;
import org.gym.dto.UserDto;
import org.gym.entity.Trainee;
import org.gym.dto.TraineeDto;
import org.gym.entity.User;
import org.gym.exception.EntityNotFoundException;
import org.gym.mapper.TraineeMapper;
import org.gym.repository.TraineeRepository;
import org.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.gym.config.Config.ENTITY_NOT_FOUND_EXCEPTION;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserNameGeneratorService userNameGeneratorService;

    @Mock
    private PasswordGeneratorService passwordGeneratorService;

    @Mock
    private TraineeMapper traineeMapper;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private final Trainee trainee;
    private final TraineeDto traineeDto;
    private Trainee traineeForUpdate;
    private Trainee traineeUpdated;
    private TraineeDto traineeDtoUpdated;
    private final String userNameForTrainee;

    private final String passwordForUser = "AAAAAAAAAA";
    private final String newPassword = "BBBBBBBBBB";
    private final String userNameNotFound = "bbbbbbb";

    UserDto userDto;
    User user;

    {
        userDto = new UserDto("Maria", "Petrenko", "Maria.Petrenko", true);
        user = new User(null, "Maria", "Petrenko", "Maria.Petrenko", passwordForUser, true);

        traineeDto = TraineeDto.builder()
                .user(userDto)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        trainee = Trainee.builder()
                .user(user)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        userNameForTrainee = trainee.getUser().getUserName();
    }

    @Test
    void selectIfExist() {
        when(traineeRepository.findByUserName(userNameForTrainee)).thenReturn(Optional.ofNullable(trainee));
        when(traineeMapper.convertToDto(trainee)).thenReturn(traineeDto);

        traineeService.select(userNameForTrainee);

        verify(traineeRepository, times(1)).findByUserName(userNameForTrainee);
        verify(traineeMapper, times(1)).convertToDto(trainee);
    }

    @Test
    void selectNotFound() {
        String exceptionMessage = String.format(ENTITY_NOT_FOUND_EXCEPTION, "findByUserName", userNameNotFound);
        when(traineeRepository.findByUserName(userNameNotFound)).thenThrow(new EntityNotFoundException(exceptionMessage));
        assertThrows(EntityNotFoundException.class, () -> traineeService.select(userNameNotFound), exceptionMessage);
        verify(traineeRepository, times(1)).findByUserName(userNameNotFound);
    }

    @Test
    void selectNullThenException() {
        String exceptionMessage = String.format(ENTITY_NOT_FOUND_EXCEPTION, "findByUserName", null);
        assertThrows(NoSuchElementException.class, () -> traineeService.select(null), exceptionMessage);
        verify(traineeRepository, times(1)).findByUserName(null);
    }

    @Test
    void createTraineeSuccessfully() {
        when(userNameGeneratorService.generate(trainee.getUser().getFirstName(), trainee.getUser().getLastName())).thenReturn("John.Doe");
        when(passwordGeneratorService.generate()).thenReturn("AAAAAAAAAA");
        when(traineeRepository.save(trainee)).thenReturn(trainee);
        when(traineeMapper.convertToEntity(traineeDto)).thenReturn(trainee);
        when(traineeMapper.convertToDto(trainee)).thenReturn(traineeDto);

        TraineeDto traineeDtoActual = traineeService.create(traineeDto);

        assertNotNull(traineeDtoActual);
        assertEquals(traineeDto, traineeDtoActual);
        verify(userNameGeneratorService, times(1)).generate(any(String.class), any(String.class));
        verify(passwordGeneratorService, times(1)).generate();
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void updateExistingTraineeSuccessfully() {
        UserDto userDto = new UserDto("John", "Doe", "John.Doe", true);
        TraineeDto traineeDto = TraineeDto.builder()
                .user(userDto)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        User userForUpdate = new User(2L, "Maria", "Ivanova", "Maria.Ivanova", "BBBBBBBBBB", true);
        traineeForUpdate = Trainee.builder()
                .id(2L)
                .user(userForUpdate)
                .dateOfBirth(LocalDate.of(2000, 2, 13))
                .address("Kyiv, Soborna str. 35, ap. 26")
                .build();

        User userUpdated = new User(2L, "John", "Doe", "John.Doe", "BBBBBBBBBB", true);
        traineeUpdated = Trainee.builder()
                .id(2L)
                .user(userUpdated)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        UserDto userDtoUpdated = new UserDto("John", "Doe", "John.Doe", true);
        traineeDtoUpdated = TraineeDto.builder()
                .user(userDtoUpdated)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();


        when(traineeRepository.findByUserName(userForUpdate.getUserName())).thenReturn(Optional.ofNullable(traineeForUpdate));
        when(userNameGeneratorService.generate(userDto.getFirstName(), userDto.getLastName())).thenReturn(userDto.getUserName());
        //it's the key point - the service has prepared the correct entity for the update:
        when(traineeRepository.save(traineeUpdated)).thenReturn(traineeUpdated);
        when(traineeMapper.convertToDto(traineeUpdated)).thenReturn(traineeDtoUpdated);

        TraineeDto traineeDtoActual = traineeService.update(userForUpdate.getUserName(), traineeDto);

        assertNotNull(traineeDtoActual);
        assertAll(
                "Grouped assertions of selected traineeDto",
                () -> assertEquals(traineeDtoUpdated.getUser().getFirstName(), traineeDtoActual.getUser().getFirstName(), "firstName should be Maria"),
                () -> assertEquals(traineeDtoUpdated.getUser().getLastName(), traineeDtoActual.getUser().getLastName(), "lastName should be Petrenko"),
                () -> assertEquals(traineeDtoUpdated.getAddress(), traineeDtoActual.getAddress(), "addresses should be equal"),
                () -> assertEquals(traineeDtoUpdated.getDateOfBirth(), traineeDtoActual.getDateOfBirth(), "dates of birth should be equal")
        );

        verify(traineeRepository, times(1)).findByUserName("Maria.Ivanova");
        verify(traineeRepository, times(1)).save(traineeUpdated);
    }

    @Test
    void deleteTraineeSuccessfully() {
        when(traineeRepository.findByUserName(userNameForTrainee)).thenReturn(Optional.ofNullable(trainee));

        traineeService.delete(userNameForTrainee);

        verify(traineeRepository, times(1)).delete(userNameForTrainee);
    }

    @Test
    void changeStatusSuccessfullyWhenStatusDifferent() {
        when(traineeRepository.findByUserName(userNameForTrainee)).thenReturn(Optional.ofNullable(trainee));
        when(traineeRepository.save(trainee)).thenReturn(trainee);
        when(traineeMapper.convertToDto(trainee)).thenReturn(traineeDto);

        traineeService.changeStatus(userNameForTrainee, !trainee.getUser().getIsActive());

        verify(traineeRepository, times(1)).findByUserName(userNameForTrainee);
        verify(traineeRepository, times(1)).save(trainee);
        verify(traineeMapper, times(1)).convertToDto(trainee);
    }

    @Test
    void changeStatusSuccessfullyWhenStatusTheSame() {
        when(traineeRepository.findByUserName(userNameForTrainee)).thenReturn(Optional.ofNullable(trainee));
        when(traineeRepository.save(trainee)).thenReturn(trainee);
        when(traineeMapper.convertToDto(trainee)).thenReturn(traineeDto);

        traineeService.changeStatus(userNameForTrainee, trainee.getUser().getIsActive());

        verify(traineeRepository, times(1)).findByUserName(userNameForTrainee);
        verify(traineeRepository, never()).save(trainee);
        verify(traineeMapper, times(1)).convertToDto(trainee);
    }

    @Test
    void changePasswordSuccessfully() {
        when(traineeRepository.findByUserName(userNameForTrainee)).thenReturn(Optional.ofNullable(trainee));

        traineeService.changePassword(userNameForTrainee, newPassword);

        verify(traineeRepository, times(1)).findByUserName(userNameForTrainee);
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }
}
