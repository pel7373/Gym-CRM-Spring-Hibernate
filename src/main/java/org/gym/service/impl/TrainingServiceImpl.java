package org.gym.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.dto.TrainingDto;
import org.gym.entity.Trainee;
import org.gym.entity.Trainer;
import org.gym.entity.Training;
import org.gym.entity.TrainingType;
import org.gym.mapper.TrainingMapper;
import org.gym.repository.TraineeRepository;
import org.gym.repository.TrainerRepository;
import org.gym.repository.TrainingRepository;
import org.gym.repository.TrainingTypeRepository;
import org.gym.service.TrainingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public TrainingDto create(TrainingDto trainingDto) {
        String trainingTypeName = trainingDto.getTrainingType().getTrainingTypeName();
        TrainingType trainingType = trainingTypeRepository.findByName(trainingTypeName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("TrainingType %s  wasn't found", trainingTypeName)));

        String trainerUserName = trainingDto.getTrainer().getUser().getUserName();
        Trainer trainer = trainerRepository.findByUserName(trainerUserName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Trainer %s wasn't found", trainerUserName)));

        String traineeUserName = trainingDto.getTrainee().getUser().getUserName();
        Trainee trainee = traineeRepository.findByUserName(traineeUserName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Trainee %s wasn't found", traineeUserName)));

        Training training = trainingMapper.convertToEntity(trainingDto);
        training.setTrainingType(trainingType);
        training.setTrainer(trainer);
        training.setTrainee(trainee);

        Training createdTraining = trainingRepository.save(training);
        LOGGER.info("Training {} was created", createdTraining);

        return trainingMapper.convertToDto(createdTraining);
    }

    @Override
    public List<TrainingDto> getTraineeTrainingsListCriteria(String traineeUserName, LocalDate fromDate,
                                                             LocalDate toDate, String trainerName, String trainingType) {

        traineeRepository.findByUserName(traineeUserName).orElseThrow(
                () -> new EntityNotFoundException(String.format("Trainee %s wasn't found", traineeUserName))
        );

        return trainingRepository.getByTraineeCriteria(traineeUserName, fromDate, toDate, trainerName, trainingType).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }

    @Override
    public List<TrainingDto> getTrainerTrainingsListCriteria(String trainerUserName, LocalDate fromDate,
                                                             LocalDate toDate, String traineeName) {
        trainerRepository.findByUserName(trainerUserName).orElseThrow(
                () -> new EntityNotFoundException(String.format("Trainer %s wasn't found", trainerUserName))
        );
        return trainingRepository.getByTrainerCriteria(trainerUserName, fromDate, toDate, traineeName).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }
}
