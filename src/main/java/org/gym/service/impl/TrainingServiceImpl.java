package org.gym.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.entity.Training;
import org.example.gymcrmsystem.entity.TrainingType;
import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.mapper.TrainingMapper;
import org.example.gymcrmsystem.repository.TrainingTypeRepository;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public TrainingDto add(@Valid TrainingDto trainingDto) {
        String trainingTypeName = trainingDto.getTrainingType().getTrainingTypeName();
        TrainingType trainingType = trainingTypeRepository.findByName(trainingTypeName).orElseThrow(
                () -> new EntityNotFoundException("TrainingType with name " + trainingTypeName + " wasn't found")
        );

        Training training = trainingMapper.convertToEntity(trainingDto);
        training.setTrainingType(trainingType);

        Training addedTraining = trainingRepository.save(training);
        LOGGER.info("Added new Training with ID {}", addedTraining.getId());
        return trainingMapper.convertToDto(addedTraining);
    }

    public List<TrainingDto> getTraineeTrainingsListCriteria(String traineeUsername, LocalDate fromDate,
                                                             LocalDate toDate, String trainerName, String trainingType) {

        return trainingRepository.getByTraineeCriteria(traineeUsername, fromDate, toDate, trainerName, trainingType).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }

    public List<TrainingDto> getTrainerTrainingsListCriteria(String trainerUsername, LocalDate fromDate,
                                                             LocalDate toDate, String traineeName) {

        return trainingRepository.getByTrainerCriteria(trainerUsername, fromDate, toDate, traineeName).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }
}
