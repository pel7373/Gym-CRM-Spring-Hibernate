package org.gym.service;

import org.gym.dto.*;
import org.gym.entity.*;
import org.gym.exception.EntityNotFoundException;
import org.gym.mapper.TrainingMapper;
import org.gym.repository.TraineeRepository;
import org.gym.repository.TrainerRepository;
import org.gym.repository.TrainingRepository;
import org.gym.repository.TrainingTypeRepository;
import org.gym.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.gym.config.Config.ENTITY_NOT_FOUND_EXCEPTION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TrainingServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private TrainingDto trainingDto;
    private Trainee trainee;
    private TrainingType trainingType;
    private String trainingTypeName;
    private Training training;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainingTypeName = "Zumba";
        TrainingTypeDto trainingTypeDto = TrainingTypeDto.builder()
                .trainingTypeName(trainingTypeName)
                .build();

        TraineeDto traineeDto = TraineeDto.builder()
                .user(new UserDto("Maria", "Petrenko", "Maria.Petrenko", true))
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        TrainerDto trainerDto = TrainerDto.builder()
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

        trainee = Trainee.builder()
                .user(new User(null, "Maria", "Petrenko", "Maria.Petrenko", "", true))
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        trainer = Trainer.builder()
                .user(new User(null, "Petro", "Ivanenko", "Petro.Ivanenko", "", true))
                .specialization(TrainingType.builder()
                        .trainingTypeName(trainingTypeName)
                        .build())
                .build();

        trainingType = TrainingType.builder()
                .trainingTypeName(trainingTypeName)
                .build();

        training = Training.builder()
                .trainingType(trainingType)
                .trainer(trainer)
                .trainee(trainee)
                .trainingName("Zumba next workout")
                .trainingType(trainingType)
                .date(LocalDate.now().plusDays(3))
                .duration(45)
                .build();
    }

    @Test
    void createTrainingSuccessfully() {

        String trainerUserName = trainingDto.getTrainer().getUser().getUserName();
        String traineeUserName = trainingDto.getTrainee().getUser().getUserName();
        when(trainingTypeRepository.findByName(trainingTypeName)).thenReturn(Optional.ofNullable(trainingType));
        when(trainerRepository.findByUserName(trainerUserName)).thenReturn(Optional.ofNullable(trainer));
        when(traineeRepository.findByUserName(traineeUserName)).thenReturn(Optional.ofNullable(trainee));
        when(trainingMapper.convertToEntity(trainingDto)).thenReturn(training);
        when(trainingRepository.save(training)).thenReturn(training);
        when(trainingMapper.convertToDto(training)).thenReturn(trainingDto);

        TrainingDto createdTrainingDto = trainingService.create(trainingDto);

        assertAll(
                () -> assertNotNull(createdTrainingDto),
                () -> assertEquals(trainingDto.getTrainer().getUser().getUserName(),
                        createdTrainingDto.getTrainer().getUser().getUserName(),
                        "trainer's userNames should be equal"),
                () -> assertEquals(trainingDto.getTrainee().getUser().getUserName(),
                        createdTrainingDto.getTrainee().getUser().getUserName(),
                        "trainer's userNames should be equal"),
                () -> assertEquals(trainingDto.getTrainingType().getTrainingTypeName(),
                        createdTrainingDto.getTrainingType().getTrainingTypeName())
        );

        verify(trainingTypeRepository, times(1)).findByName(trainingTypeName);
        verify(trainerRepository, times(1)).findByUserName(trainerUserName);
        verify(traineeRepository, times(1)).findByUserName(traineeUserName);
        verify(trainingMapper, times(1)).convertToEntity(trainingDto);
        verify(trainingRepository, times(1)).save(training);
        verify(trainingMapper, times(1)).convertToDto(training);
    }

    @Test
    void createTrainingTrainingTypeNotFound() {
        String notValidTypeName = "NotValidTypeName";
        trainingDto.setTrainingType(TrainingTypeDto.builder()
                .trainingTypeName(notValidTypeName)
                .build());

        when(trainingTypeRepository.findByName(notValidTypeName))
                .thenThrow(new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_EXCEPTION, notValidTypeName)));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> trainingService.create(trainingDto));
        assertEquals(String.format(ENTITY_NOT_FOUND_EXCEPTION, notValidTypeName),
                exception.getMessage());
        verify(trainingRepository, never()).save(any(Training.class));
    }

    @Test
    void getTraineeTrainingsListCriteriaSuccess() {
        LocalDate fromDate = LocalDate.now().minusYears(10);
        LocalDate toDate = LocalDate.now().plusYears(10);

        List<Training> trainings = List.of(Training.builder()
                .trainee(Trainee.builder().user(User.builder().userName("Maria.Petrenko").build()).build())
                .trainer(Trainer.builder().user(User.builder().userName("Petro.Ivanenko").build()).build())
                .trainingType(TrainingType.builder().trainingTypeName("Zumba").build())
                .build());

        when(trainingRepository.getByTraineeCriteria(any(), any(), any(), any(), any()))
                .thenReturn(trainings);
        when(trainingMapper.convertToEntity(any())).thenReturn(training);
        when(trainingMapper.convertToDto(any())).thenReturn(trainingDto);

        when(trainingTypeRepository.findByName(any())).thenReturn(Optional.of(trainingType));
        when(trainerRepository.findByUserName(any())).thenReturn(Optional.of(trainer));
        when(traineeRepository.findByUserName(any())).thenReturn(Optional.of(trainee));
        when(trainingRepository.save(any())).thenReturn(training);

        List<TrainingDto> result =
                trainingService.getTraineeTrainingsListCriteria(
                        "Maria.Petrenko", fromDate, toDate,
                        "Petro.Ivanenko", "Zumba");

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(1, result.size()),
            () -> assertEquals("Zumba", result.get(0).getTrainingType().getTrainingTypeName()),
            () -> assertEquals("Petro.Ivanenko", result.get(0).getTrainer().getUser().getUserName())
        );
        verify(trainingRepository, times(1))
                .getByTraineeCriteria(any(), any(), any(), any(), any());

        verify(trainingMapper, times(1)).convertToDto(any());
        verify(traineeRepository, times(1)).findByUserName(any());
        verify(trainerRepository, never()).findByUserName(any());
        verify(trainingMapper, never()).convertToEntity(any());
        verify(trainingTypeRepository, never()).findByName(any());
    }

    @Test
    void getTrainerTrainingsListCriteriaSuccess() {
        LocalDate fromDate = LocalDate.now().minusYears(10);
        LocalDate toDate = LocalDate.now().plusYears(10);

        Trainer trainerForSearch = Trainer.builder().user(User.builder().userName("trainerUserName").build()).build();
        List<Training> trainings = List.of(Training.builder()
                .trainer(trainerForSearch)
                .trainee(Trainee.builder().user(User.builder().userName("traineeUserName").build()).build())
                .trainingType(TrainingType.builder().trainingTypeName("Zumba").build())
                .build());

        when(trainingRepository.getByTrainerCriteria(any(), any(), any(), any())).thenReturn(trainings);
        when(trainerRepository.findByUserName("trainerUserName")).thenReturn(Optional.ofNullable(trainerForSearch));
        when(trainingMapper.convertToDto(any())).thenReturn(trainingDto);

        List<TrainingDto> result = trainingService.getTrainerTrainingsListCriteria(
                "trainerUserName", fromDate, toDate, "Maria.Petrenko");

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals("Zumba", result.get(0).getTrainingType().getTrainingTypeName()),
                () -> assertEquals("Maria.Petrenko", result.get(0).getTrainee().getUser().getUserName())
        );

        verify(trainingRepository, times(1))
                .getByTrainerCriteria(any(), any(), any(), any());
    }
}
