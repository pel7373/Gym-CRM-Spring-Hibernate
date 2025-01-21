package org.gym.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
import org.springframework.validation.annotation.Validated;

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
                .orElseThrow(() -> new EntityNotFoundException("TrainingType with name " + trainingTypeName + " wasn't found"));

        Trainer trainer = trainerRepository.findByUserName(trainingDto.getTrainer().getUser().getUserName())
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        Trainee trainee = traineeRepository.findByUserName(trainingDto.getTrainee().getUser().getUserName())
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found"));

        Training training = trainingMapper.convertToEntity(trainingDto);
        training.setTrainingType(trainingType);
        training.setTrainer(trainer);
        training.setTrainee(trainee);

        Training addedTraining = trainingRepository.save(training);
        LOGGER.info("Added new Training with ID {}", addedTraining.getId());

        return trainingMapper.convertToDto(addedTraining);
    }

    @Override
    public List<TrainingDto> getTraineeTrainingsListCriteria(String traineeUserName, LocalDate fromDate,
                                                             LocalDate toDate, String trainerName, String trainingType) {

        traineeRepository.findByUserName(traineeUserName).orElseThrow(
                () -> new EntityNotFoundException("Trainee with userName " + traineeUserName + " wasn't found")
        );

        return trainingRepository.getByTraineeCriteria(traineeUserName, fromDate, toDate, trainerName, trainingType).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }

    @Override
    public List<TrainingDto> getTrainerTrainingsListCriteria(String trainerUserName, LocalDate fromDate,
                                                             LocalDate toDate, String traineeName) {
        trainerRepository.findByUserName(trainerUserName).orElseThrow(
                () -> new EntityNotFoundException("Trainer with userName " + trainerUserName + " wasn't found")
        );
        return trainingRepository.getByTrainerCriteria(trainerUserName, fromDate, toDate, traineeName).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }
}
