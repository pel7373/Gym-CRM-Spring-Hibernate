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
        TrainingType trainingType = trainingTypeRepository.findByName(trainingTypeName).get();

        String trainerUserName = trainingDto.getTrainer().getUser().getUserName();
        Trainer trainer = trainerRepository.findByUserName(trainerUserName).get();

        String traineeUserName = trainingDto.getTrainee().getUser().getUserName();
        Trainee trainee = traineeRepository.findByUserName(traineeUserName).get();

        Training training = trainingMapper.convertToEntity(trainingDto);

        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);

        Training createdTraining = trainingRepository.save(training);
        LOGGER.info("Training with id {} was created", createdTraining.getId());
        return trainingMapper.convertToDto(createdTraining);
    }

    @Override
    public List<TrainingDto> getTraineeTrainingsListCriteria(String traineeUserName, LocalDate fromDate,
                                                             LocalDate toDate, String trainerUserName, String trainingType) {

        traineeRepository.findByUserName(traineeUserName).get();

        return trainingRepository.getByTraineeCriteria(traineeUserName, fromDate, toDate, trainerUserName, trainingType).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }

    @Override
    public List<TrainingDto> getTrainerTrainingsListCriteria(String trainerUserName, LocalDate fromDate,
                                                             LocalDate toDate, String traineeUserName) {
        trainerRepository.findByUserName(trainerUserName).orElseThrow(
                () -> new EntityNotFoundException(String.format("Trainer %s wasn't found", trainerUserName))
        );
        return trainingRepository.getByTrainerCriteria(trainerUserName, fromDate, toDate, traineeUserName).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }
}
