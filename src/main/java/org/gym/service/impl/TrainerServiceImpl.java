package org.gym.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gymcrmsystem.mapper.TrainingTypeMapper;
import org.example.gymcrmsystem.entity.Trainee;
import org.example.gymcrmsystem.entity.TrainingType;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.entity.Trainer;
import org.example.gymcrmsystem.repository.TrainingTypeRepository;
import org.example.gymcrmsystem.service.TrainerService;
import org.example.gymcrmsystem.utils.PasswordGenerator;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class TrainerServiceImpl implements TrainerService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final UsernameGenerator usernameGenerator;
    private final PasswordGenerator passwordGenerator;
    private final TrainerMapper trainerMapper;
    private final TrainingTypeMapper trainingTypeMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TrainerDto create(@Valid TrainerDto trainerDto) {
        trainerDto.getUser().setPassword(passwordEncoder.encode(passwordGenerator.generateRandomPassword()));
        trainerDto.getUser().setUsername(usernameGenerator.generateUniqueUsername(trainerDto.getUser()));
        String trainingTypeName = trainerDto.getSpecialization().getTrainingTypeName();

        TrainingType trainingType = trainingTypeRepository.findByName(trainingTypeName).orElseThrow(
                () -> new EntityNotFoundException("TrainingType with name " + trainingTypeName + " wasn't found")
        );

        Trainer trainer = trainerMapper.convertToEntity(trainerDto);

        trainer.setSpecialization(trainingType);
        Trainer savedTrainer = trainerRepository.save(trainer);
        LOGGER.info("Created new Trainer with username {}", savedTrainer.getUser().getUsername());

        return trainerMapper.convertToDto(savedTrainer);
    }

    @Override
    public TrainerDto select(String username) {
        LOGGER.info("Selecting trainee with username {}", username);
        Trainer trainer = trainerRepository.findByUsername(username).orElseThrow(
                () -> {
                    LOGGER.debug("Trainee with username {} not found", username);
                    return new EntityNotFoundException("Trainee with username " + username + " wasn't found");
                }
        );
        return trainerMapper.convertToDto(trainer);
    }

    @Override
    @Transactional
    public TrainerDto update(String username, @Valid TrainerDto trainerDto) {
        LOGGER.info("Updating Trainer with username {}", username);
        Trainer existingTrainer = trainerRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Trainer with username " + username + " wasn't found")
        );

        String trainingTypeName = trainerDto.getSpecialization().getTrainingTypeName();

        TrainingType trainingType = trainingTypeRepository.findByName(trainingTypeName).orElseThrow(
                () -> new EntityNotFoundException("TrainingType with name " + trainingTypeName + " wasn't found")
        );

        existingTrainer.setSpecialization(trainingType);
        existingTrainer.getUser().setFirstName(trainerDto.getUser().getFirstName());
        existingTrainer.getUser().setLastName(trainerDto.getUser().getLastName());
        existingTrainer.getUser().setUsername(usernameGenerator.generateUniqueUsername(trainerDto.getUser()));
        existingTrainer.getUser().setPassword(passwordEncoder.encode(trainerDto.getUser().getPassword()));
        existingTrainer.setSpecialization(trainingTypeMapper.convertToEntity(trainerDto.getSpecialization()));

        Trainer updatedTrainer = trainerRepository.save(existingTrainer);
        LOGGER.info("Trainer with username {} updated successfully", updatedTrainer.getUser().getUsername());

        return trainerMapper.convertToDto(updatedTrainer);
    }

    @Override
    public boolean authenticateTrainer(String username, String password) {
        Trainer existingTrainer = trainerRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("TrainingType with username " + username + " wasn't found")
        );
        return passwordEncoder.matches(password, existingTrainer.getUser().getPassword());
    }

    @Override
    @Transactional
    public void changeStatus(String username, Boolean isActive) {
        Trainer existingTrainer = trainerRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Trainer with username " + username + " wasn't found")
        );
        existingTrainer.getUser().setIsActive(isActive);
    }

    @Override
    @Transactional
    public void changePassword(String username, String lastPassword, String newPassword) {
        Trainer existingTrainer = trainerRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + username + " wasn't found")
        );
        if (passwordEncoder.matches(lastPassword, existingTrainer.getUser().getPassword())) {
            existingTrainer.getUser().setPassword(newPassword);
            trainerRepository.save(existingTrainer);
        } else {
            throw new IllegalArgumentException("Wrong password");
        }
    }

    @Override
    public List<TrainerDto> getUnassignedTrainersList(String traineeUsername) {
        Trainee existingTrainee = traineeRepository.findByUsername(traineeUsername).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + traineeUsername + " wasn't found")
        );
        List<Trainer> trainers = trainerRepository.findAll();

        return trainers.stream()
                .filter(trainer -> !trainer.getTrainees().contains(existingTrainee))
                .map(trainerMapper::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames) {
        Trainee existingTrainee = traineeRepository.findByUsername(traineeUsername).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + traineeUsername + " wasn't found")
        );

        List<Trainer> trainers = trainersUsernames.stream()
                .map(trainerRepository::findByUsername)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        existingTrainee.setTrainers(trainers);
        traineeRepository.save(existingTrainee);

        return trainers.stream()
                .map(trainerMapper::convertToDto)
                .toList();
    }
}
