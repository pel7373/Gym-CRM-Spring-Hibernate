package org.gym.facade;

import jakarta.transaction.Transactional;
import org.gym.dto.TraineeDto;
import org.gym.dto.UserDto;
import org.gym.entity.Trainee;
import org.gym.entity.User;
import org.gym.facade.impl.TraineeFacadeImpl;
import org.gym.facade.impl.UserNamePasswordValidator;
import org.gym.mapper.TraineeMapper;
import org.gym.service.TraineeService;
import org.gym.validator.UserDtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@Transactional
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TraineeFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private UserDtoValidator userDtoValidator;

    @Mock
    private UserNamePasswordValidator userNamePasswordValidator;

    @InjectMocks
    private TraineeFacadeImpl traineeFacade;

    @Mock
    private TraineeMapper traineeMapper;

    private TraineeDto traineeDto;
    private TraineeDto traineeDtoNotValid;
    private Trainee trainee;
    private String passwordForUser = "AAAAAAAAAA";
    private String newPassword = "BBBBBBBBBB";
    String userNameForTraineeDto;
    String userNameNotFound = "bbbbbbb";

    @BeforeEach
    void setUp() {
        UserDto userDto = new UserDto("Maria", "Petrenko", "Maria.Petrenko", true);
        UserDto userDtoNotValid = new UserDto("Pa", "Pa", "Maria.Petrenko2", false);

        User user = new User(null, "Maria", "Petrenko", "Maria.Petrenko", passwordForUser, true);

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

        userNameForTraineeDto = traineeDto.getUser().getFirstName();

        traineeDtoNotValid
                = new TraineeDto(userDtoNotValid, LocalDate.of(1995, 1, 23),
                "Kyiv, Soborna str. 35, ap. 26");
    }

    @Test
    void createTraineeSuccessfully() {
        when(userDtoValidator.validate(any(UserDto.class))).thenReturn(true);
        when(traineeService.create(traineeDto)).thenReturn(traineeDto);

        traineeFacade.create(traineeDto);

        verify(userDtoValidator, times(1)).validate(traineeDto.getUser());
        verify(traineeService, times(1)).create(any(TraineeDto.class));
    }

    @Test
    void createTraineeNullFail() {
        TraineeDto createdTraineeDto = traineeFacade.create(null);
        assertNull(createdTraineeDto);
        verify(userDtoValidator, never()).validate(traineeDto.getUser());
        verify(traineeService, never()).create(any(TraineeDto.class));
    }

    @Test
    void createTraineeNotValidFail() {
        when(userDtoValidator.validate(traineeDtoNotValid.getUser())).thenReturn(false);
        TraineeDto createdTraineeDto = traineeFacade.create(traineeDtoNotValid);

        assertNull(createdTraineeDto);
        verify(userDtoValidator, times(1)).validate(traineeDtoNotValid.getUser());
        verify(traineeService, never()).create(any(TraineeDto.class));
    }

    @Test
    void selectTraineeSuccessfully() {
        String userNameForTraineeDto = traineeDto.getUser().getFirstName();
        when(userNamePasswordValidator.isNullOrBlank(any(String.class), any(String.class))).thenReturn(false);
        when(traineeService.authenticate(userNameForTraineeDto, passwordForUser)).thenReturn(true);
        when(traineeService.select(userNameForTraineeDto)).thenReturn(traineeDto);

        traineeFacade.select(userNameForTraineeDto, passwordForUser);

        verify(traineeService, times(1)).authenticate(any(String.class), any(String.class));
        verify(traineeService, times(1)).authenticate(any(String.class), any(String.class));
        verify(traineeService, times(1)).select(userNameForTraineeDto);
    }

    @Test
    void selectTraineeNotFound() {
        when(userNamePasswordValidator.isNullOrBlank(userNameNotFound, passwordForUser)).thenReturn(false);
        when(traineeService.authenticate(userNameNotFound, passwordForUser)).thenReturn(false);
        when(traineeService.select(userNameNotFound)).thenReturn(traineeDto);

        traineeFacade.select(userNameNotFound, passwordForUser);

        assertDoesNotThrow(() -> traineeService.select(userNameNotFound));
        verify(traineeService, times(1)).authenticate(any(String.class), any(String.class));
        verify(traineeService, times(1)).select(userNameNotFound);
    }

    @Test
    void updateTraineeSuccessfully() {
        when(userNamePasswordValidator.isNullOrBlank(userNameForTraineeDto, passwordForUser)).thenReturn(false);
        when(userDtoValidator.validate(traineeDto.getUser())).thenReturn(true);
        when(traineeService.authenticate(userNameForTraineeDto, passwordForUser)).thenReturn(true);
        when(traineeService.update(userNameForTraineeDto, traineeDto)).thenReturn(traineeDto);

        traineeFacade.update(userNameForTraineeDto, passwordForUser, traineeDto);

        verify(userNamePasswordValidator, times(1)).isNullOrBlank(any(String.class), any(String.class));
        verify(userDtoValidator, times(1)).validate(traineeDto.getUser());
        verify(traineeService, times(1)).authenticate(userNameForTraineeDto, passwordForUser);
        verify(traineeService, times(1)).update(userNameForTraineeDto, traineeDto);
    }

    @Test
    void updateTraineeNullFail() {
        TraineeDto createdTraineeDto = traineeFacade.update("aaa", "aaa", null);

        assertNull(createdTraineeDto);
        verify(userDtoValidator, never()).validate(traineeDto.getUser());
        verify(traineeService, never()).create(any(TraineeDto.class));
        verify(traineeService, never()).authenticate(any(String.class), any(String.class));
    }

    @Test
    void updateTraineeNotValidFail() {
        when(userDtoValidator.validate(traineeDtoNotValid.getUser())).thenReturn(false);
        when(userNamePasswordValidator.isNullOrBlank(userNameForTraineeDto, passwordForUser)).thenReturn(false);
        TraineeDto createdTraineeDto = traineeFacade.update("aaa", "aaa", traineeDtoNotValid);

        assertNull(createdTraineeDto);
        verify(userDtoValidator, times(1)).validate(traineeDtoNotValid.getUser());
        verify(userNamePasswordValidator, never()).isNullOrBlank(userNameForTraineeDto, passwordForUser);
        verify(traineeService, never()).update("aaa", traineeDtoNotValid);
    }

    @Test
    void deleteTraineeSuccessfully() {
        when(userNamePasswordValidator.isNullOrBlank(userNameForTraineeDto, passwordForUser)).thenReturn(false);
        when(traineeService.authenticate(userNameForTraineeDto, passwordForUser)).thenReturn(true);

        traineeFacade.delete(userNameForTraineeDto, passwordForUser);

        verify(userNamePasswordValidator, times(1)).isNullOrBlank(any(String.class), any(String.class));
        verify(traineeService, times(1)).authenticate(userNameForTraineeDto, passwordForUser);
        verify(traineeService, times(1)).delete(userNameForTraineeDto);
    }

    @Test
    void deleteTraineeNotValidFail() {
        when(userNamePasswordValidator.isNullOrBlank(userNameNotFound, passwordForUser)).thenReturn(false);
        when(traineeService.authenticate(userNameNotFound, passwordForUser)).thenReturn(false);

        traineeFacade.delete(userNameNotFound, passwordForUser);

        verify(userNamePasswordValidator, times(1)).isNullOrBlank(userNameNotFound, passwordForUser);
        verify(traineeService, never()).delete(userNameNotFound);
    }

    @Test
    void deleteTraineeNullFail() {
        traineeFacade.delete(null, passwordForUser);

        verify(traineeService, never()).delete(null);
        verify(traineeService, times(1)).authenticate(null, passwordForUser);
    }

    @Test
    void changeStatusStatusNullFail() {
        when(userNamePasswordValidator.isNullOrBlank(userNameForTraineeDto, passwordForUser)).thenReturn(false);
        TraineeDto createdTraineeDto = traineeFacade.changeStatus("aaa", "aaa", null);

        assertNull(createdTraineeDto);
        verify(userNamePasswordValidator, never()).isNullOrBlank(userNameForTraineeDto, passwordForUser);
        verify(traineeService, never()).changeStatus("aaa", null);
    }

    @Test
    void changeStatusUserNameNotFoundFail() {
        when(userNamePasswordValidator.isNullOrBlank(userNameNotFound, passwordForUser)).thenReturn(false);
        when(traineeService.authenticate(userNameNotFound, passwordForUser)).thenReturn(false);

        TraineeDto createdTraineeDto = traineeFacade.changeStatus(userNameNotFound, passwordForUser, true);

        assertNull(createdTraineeDto);
        verify(userNamePasswordValidator, times(1)).isNullOrBlank(userNameNotFound, passwordForUser);
        verify(traineeService, times(1)).authenticate(userNameNotFound, passwordForUser);
        verify(traineeService, never()).changeStatus(userNameNotFound, true);
    }

    @Test
    void changeStatusSuccessfully() {
        when(userNamePasswordValidator.isNullOrBlank(userNameForTraineeDto, passwordForUser)).thenReturn(false);
        when(traineeService.authenticate(userNameForTraineeDto, passwordForUser)).thenReturn(true);

        traineeFacade.changeStatus(userNameForTraineeDto, passwordForUser, true);

        verify(userNamePasswordValidator, times(1)).isNullOrBlank(userNameForTraineeDto, passwordForUser);
        verify(traineeService, times(1)).authenticate(userNameForTraineeDto, passwordForUser);
        verify(traineeService, times(1)).changeStatus(userNameForTraineeDto, true);
    }

    @Test
    void authenticateSuccessfully() {
        String userNameForTraineeDto = traineeDto.getUser().getFirstName();
        when(userNamePasswordValidator.isNullOrBlank(any(String.class), any(String.class))).thenReturn(false);
        when(traineeService.authenticate(any(String.class), any(String.class))).thenReturn(true);

        traineeFacade.authenticate(userNameForTraineeDto, passwordForUser);

        verify(userNamePasswordValidator, times(1)).isNullOrBlank(any(String.class), any(String.class));
        verify(traineeService, times(1)).authenticate(any(String.class), any(String.class));
    }

    @Test
    void authenticateNotSuccessfulNullOrBlankUserNameOrPassword() {
        when(userNamePasswordValidator.isNullOrBlank(any(String.class), any(String.class))).thenReturn(true);

        traineeFacade.authenticate("aaa", "aaa");

        verify(userNamePasswordValidator, times(1)).isNullOrBlank(any(String.class), any(String.class));
        verify(traineeService, never()).authenticate(any(String.class), any(String.class));
    }

    @Test
    void authenticateNotSuccessfulUserNameNotFoundOrDoesntMatchWithPassword() {
        when(userNamePasswordValidator.isNullOrBlank(userNameNotFound, passwordForUser)).thenReturn(false);
        when(traineeService.authenticate(userNameNotFound, passwordForUser)).thenReturn(false);
        traineeFacade.authenticate(userNameNotFound, passwordForUser);

        verify(userNamePasswordValidator, times(1)).isNullOrBlank(userNameNotFound, passwordForUser);
        verify(traineeService, times(1)).authenticate(any(String.class), any(String.class));
    }

    @Test
    void changePasswordNewPasswordNullFail() {
        when(userNamePasswordValidator.isNewPasswordNullOrBlank(null)).thenReturn(true);
        TraineeDto createdTraineeDto = traineeFacade.changePassword(userNameForTraineeDto, passwordForUser, null);

        assertNull(createdTraineeDto);
        verify(userNamePasswordValidator, times(1)).isNewPasswordNullOrBlank(null);
        verify(traineeService, never()).changePassword(userNameForTraineeDto, null);
    }

    @Test
    void changePasswordUserNameNotFoundFail() {
        when(userNamePasswordValidator.isNewPasswordNullOrBlank(newPassword)).thenReturn(false);
        when(userNamePasswordValidator.isNullOrBlank(userNameNotFound, passwordForUser)).thenReturn(false);
        when(traineeService.authenticate(userNameNotFound, passwordForUser)).thenReturn(false);

        TraineeDto createdTraineeDto = traineeFacade.changePassword(userNameNotFound, passwordForUser, newPassword);

        assertNull(createdTraineeDto);
        verify(userNamePasswordValidator, times(1)).isNewPasswordNullOrBlank(newPassword);
        verify(userNamePasswordValidator, times(1)).isNullOrBlank(userNameNotFound, passwordForUser);
        verify(traineeService, times(1)).authenticate(userNameNotFound, passwordForUser);
        verify(traineeService, never()).changePassword(userNameNotFound, newPassword);
    }

    @Test
    void changePasswordSuccessfully() {
        when(userNamePasswordValidator.isNewPasswordNullOrBlank(newPassword)).thenReturn(false);
        when(userNamePasswordValidator.isNullOrBlank(userNameForTraineeDto, passwordForUser)).thenReturn(false);
        when(traineeService.authenticate(userNameForTraineeDto, passwordForUser)).thenReturn(true);

        traineeFacade.changePassword(userNameForTraineeDto, passwordForUser, newPassword);

        verify(userNamePasswordValidator, times(1)).isNewPasswordNullOrBlank(newPassword);
        verify(userNamePasswordValidator, times(1)).isNullOrBlank(userNameForTraineeDto, passwordForUser);
        verify(traineeService, times(1)).authenticate(userNameForTraineeDto, passwordForUser);
        verify(traineeService, times(1)).changePassword(userNameForTraineeDto, newPassword);
    }
}
