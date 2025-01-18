package org.gym.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.dto.TrainingDto;
import org.gym.entity.Training;
import org.gym.entity.TrainingType;
import org.gym.mapper.TrainingMapper;
import org.gym.repository.TrainingRepository;
import org.gym.repository.TrainingTypeRepository;
import org.gym.service.TrainingService;
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
    public TrainingDto create(TrainingDto trainingDto) {
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

//        return trainingRepository.getByTraineeCriteria(traineeUserName, fromDate, toDate, trainerName, trainingType).stream()
//                .map(trainingMapper::convertToDto)
//                .toList();
        return null;
    }

    public List<TrainingDto> getTrainerTrainingsListCriteria(String trainerUsername, LocalDate fromDate,
                                                             LocalDate toDate, String traineeName) {

//        return trainingRepository.getByTrainerCriteria(trainerUsername, fromDate, toDate, traineeName).stream()
//                .map(trainingMapper::convertToDto)
//                .toList();
        return null;
    }
}
