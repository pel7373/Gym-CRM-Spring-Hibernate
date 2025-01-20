package org.gym.service;

import jakarta.transaction.Transactional;
import org.gym.dto.UserDto;
import org.gym.entity.Trainee;
import org.gym.dto.TraineeDto;
import org.gym.entity.User;
import org.gym.mapper.TraineeMapper;
import org.gym.mapper.TraineeMapperImpl;
import org.gym.repository.TraineeRepository;
import org.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Spy
    private TraineeMapper spyTraineeMapper = new TraineeMapperImpl();

    private Trainee trainee;
    private TraineeDto traineeDto;
    private Trainee traineeForUpdate;
    private Trainee traineeUpdated;
    private TraineeDto traineeDtoUpdated;
    private TraineeDto traineeDto2;
    private TraineeDto traineeDtoNotValid;
    private String userNameForTrainee;

    private String passwordForUser = "AAAAAAAAAA";
    private String newPassword = "BBBBBBBBBB";
    String userNameForTraineeDto;
    String userNameNotFound = "bbbbbbb";

    @BeforeEach
    void setUp() {
        UserDto userDto = new UserDto("Maria", "Petrenko", "Maria.Petrenko", true);
        UserDto userDto2 = new UserDto("Petro", "Ivanenko", "Petro.Ivanenko", true);
        UserDto userDtoNotValid = new UserDto("Pa", "Pa", "Maria.Petrenko2", false);

        User user = new User(null, "Maria", "Petrenko", "Maria.Petrenko", "AAAAAAAAAA", true);
        User user2 = new User(null, "Petro", "Ivanenko", "Petro.Ivanenko", "AAAAAAAAAA", true);

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

        traineeDto2 = TraineeDto.builder()
                .user(userDto2)
                .dateOfBirth(LocalDate.of(1985, 1, 23))
                .address("Kyiv, Soborna str. 35, ap. 26")
                .build();

        traineeDtoNotValid
                = new TraineeDto(userDtoNotValid, LocalDate.of(1995, 1, 23),
                "Kyiv, Soborna str. 35, ap. 26");

//        trainee = Trainee.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Doe")
//                .userName("John.Doe")
//                .password("AAAAAAAAAA")
//                .isActive(true)
//                .dateOfBirth(LocalDate.of(1995, 1, 23))
//                .address("Vinnitsya, Soborna str. 35, ap. 26")
//                .build();
//        traineeDto = spyTraineeMapper.convertToDto(trainee);
//
//        traineeForUpdate = Trainee.builder()
//                .id(2L)
//                .firstName("Maria")
//                .lastName("Ivanova")
//                .userName("Maria.Ivanova")
//                .password("BBBBBBBBBB")
//                .isActive(true)
//                .dateOfBirth(LocalDate.of(2000, 2, 13))
//                .address("Kyiv, Soborna str. 35, ap. 26")
//                .build();
//
//        traineeUpdated = Trainee.builder()
//                .id(2L)
//                .firstName("John")
//                .lastName("Doe")
//                .userName("John.Doe0")
//                .password("BBBBBBBBBB")
//                .isActive(true)
//                .dateOfBirth(LocalDate.of(1995, 1, 23))
//                .address("Vinnitsya, Soborna str. 35, ap. 26")
//                .build();
//        traineeDtoUpdated = spyTraineeMapper.convertToDto(traineeUpdated);
    }

    @Test
    void selectIfExist() {
        String userName = trainee.getUser().getPassword();
        when(traineeRepository.findByUserName(userName)).thenReturn(Optional.ofNullable(trainee));
        TraineeDto mockedTraineeDto = traineeService.select(userName);
        assertNotNull(mockedTraineeDto);
        assertEquals("John", mockedTraineeDto.getUser().getFirstName());
        verify(traineeRepository, times(1)).findByUserName("AAA");
    }

//    @Test
//    void selectNotFound() {
//        when(traineeRepository.findByUserName(1L)).thenThrow(new EntityNotFoundException(String.format("Trainee with id %d wasn't found!", 1L)));
//        assertThrows(EntityNotFoundException.class, () -> traineeService.select(1L));
//        verify(traineeRepository, times(1)).findByUserName(1L);
//    }
//
//    @Test
//    void selectNullThenException() {
//        assertThrows(InvalidIdException.class, () -> traineeService.select(null));
//        verify(traineeRepository, never()).findByUserName(any(Long.class));
//    }
//
//    @Test
//    void selectNegativeIdThenException() {
//        assertThrows(InvalidIdException.class, () -> traineeService.select(-1L));
//        verify(traineeRepository, never()).findByUserName(any(Long.class));
//    }
//
//    @Test
//    void createTraineeSuccessfully() {
//        when(userNameGeneratorService.generate(traineeDtogetUser().getFirstName(), traineeDto.getLastName())).thenReturn("John.Doe");
//        when(passwordGeneratorService.generate()).thenReturn("AAAAAAAAAA");
//        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);
//
//        TraineeDto traineeDtoActual = traineeService.save(traineeDto);
//
//        assertNotNull(traineeDtoActual);
//        assertEquals(traineeDto, traineeDtoActual);
//        verify(userNameGeneratorService, times(1)).generate(any(String.class), any(String.class));
//        verify(traineeRepository, times(1)).save(any(Trainee.class));
//    }
//
//    @Test
//    void createTraineeIfNullThenNullEntityException() {
//        assertThrows(NullEntityException.class, () -> traineeService.save(null));
//        verify(traineeRepository, never()).save(any(Trainee.class));
//    }
//
//    @Test
//    void updateExistingTraineeSuccessfully() {
//        when(traineeRepository.findByUserName(2L)).thenReturn(traineeForUpdate);
//        when(userNameGeneratorService.generate("John", "Doe")).thenReturn("John.Doe0");
//        //it's the key point - the service has prepared the correct entity for the update:
//        when(traineeRepository.update(2L, traineeUpdated)).thenReturn(traineeUpdated);
//        TraineeDto traineeDtoActual = traineeService.update(2L, traineeDto);
//
//        assertNotNull(traineeDtoActual);
//        assertEquals(traineeDtoUpdated, traineeDtoActual);
//        verify(traineeRepository, times(1)).findByUserName(2L);
//        verify(traineeRepository, times(1)).update(eq(2L), any(Trainee.class));
//    }
//
//    @Test
//    void updateNonExistingTraineeThenException() {
//        when(traineeRepository.findByUserName(3L)).thenThrow(new EntityNotFoundException(" "));
//        assertThrows(EntityNotFoundException.class, () -> traineeService.update(3L, traineeDto));
//        verify(traineeRepository, times(1)).findByUserName(3L);
//        verify(traineeRepository, never()).update(any(Long.class), any(Trainee.class));
//    }
//
//    @Test
//    void updateNullTraineeThenException() {
//        assertThrows(NullEntityException.class, () -> traineeService.update(3L, null));
//        verify(traineeRepository, never()).findByUserName(any(Long.class));
//        verify(traineeRepository, never()).update(any(Long.class), any(Trainee.class));
//    }
//
//    @Test
//    void updateNullIdThenException() {
//        assertThrows(InvalidIdException.class, () -> traineeService.update(null, traineeDto));
//        verify(traineeRepository, never()).findByUserName(any(Long.class));
//        verify(traineeRepository, never()).update(any(Long.class), any(Trainee.class));
//    }
//
//    @Test
//    void updateNegativeIdThenException() {
//        assertThrows(InvalidIdException.class, () -> traineeService.update(-1L, traineeDto));
//        verify(traineeRepository, never()).findByUserName(any(Long.class));
//        verify(traineeRepository, never()).update(any(Long.class), any(Trainee.class));
//    }
//
//    @Test
//    void deleteByIdTraineeSuccessfully() {
//        traineeService.deleteById(2L);
//        verify(traineeRepository, times(1)).deleteById(2L);
//    }
//
//    @Test
//    void deleteByIdNullIdThenException() {
//        assertThrows(InvalidIdException.class, () -> traineeService.deleteById(null));
//        verify(traineeRepository, never()).deleteById(any(Long.class));
//    }
//
//    @Test
//    void deleteByIdNegativeIdThenException() {
//        assertThrows(InvalidIdException.class, () -> traineeService.deleteById(-1L));
//        verify(traineeRepository, never()).deleteById(any(Long.class));
//    }
}
