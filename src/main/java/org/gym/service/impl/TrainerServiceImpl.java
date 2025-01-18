package org.gym.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.dto.TrainerDto;
import org.gym.exception.EntityNotFoundException;
import org.gym.exception.NullEntityException;
import org.gym.mapper.TrainerMapper;
import org.gym.mapper.TrainingTypeMapper;
import org.gym.repository.TraineeRepository;
import org.gym.repository.TrainerRepository;
import org.gym.repository.TrainingTypeRepository;
import org.gym.service.PasswordGeneratorService;
import org.gym.service.TrainerService;
import org.gym.service.UserNameGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class TrainerServiceImpl implements TrainerService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final UserNameGeneratorService userNameGeneratorService;
    private final PasswordGeneratorService passwordGeneratorService;
    private final TrainerMapper trainerMapper;
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    public TrainerDto create(TrainerDto trainerDTO) throws NullEntityException {
        return null;
    }

    @Override
    public TrainerDto select(String username) throws EntityNotFoundException {
        return null;
    }

    @Override
    public TrainerDto update(String username, TrainerDto trainerDto) throws EntityNotFoundException {
        return null;
    }

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

    @Override
    public void changeStatus(String username, Boolean isActive) {

    }

    @Override
    public void changePassword(String username,  String newPassword) {

    }

    @Override
    public List<TrainerDto> getUnassignedTrainersList(String traineeUsername) {
        return List.of();
    }

    @Override
    public List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames) {
        return List.of();
    }


//    @Override
//    public TrainerDto create(@Valid TrainerDto trainerDto) {
//        trainerDto.getUser().setPassword(passwordGeneratorService.generate());
//        trainerDto.getUser()
//                .setUsername(userNameGeneratorService
//                        .generate(trainerDto.getUser().getFirstName(), trainerDto.getUser().getLastName()));
//        String trainingTypeName = trainerDto.getSpecialization().getTrainingTypeName();
//
//        TrainingType trainingType = trainingTypeRepository.findByName(trainingTypeName).orElseThrow(
//                () -> new EntityNotFoundException("TrainingType with name " + trainingTypeName + " wasn't found")
//        );
//
//        Trainer trainer = trainerMapper.convertToEntity(trainerDto);
//
//        trainer.setSpecialization(trainingType);
//        Trainer savedTrainer = trainerRepository.save(trainer);
//        LOGGER.info("Created new Trainer with username {}", savedTrainer.getUser().getUserName());
//
//        return trainerMapper.convertToDto(savedTrainer);
//    }
//
//    @Override
//    public TrainerDto select(String userName) {
//        LOGGER.info("Selecting trainee with username {}", userName);
//        Trainer trainer = trainerRepository.findByUserName(userName).orElseThrow(
//                () -> {
//                    LOGGER.debug("Trainee with username {} not found", userName);
//                    return new EntityNotFoundException("Trainee with username " + username + " wasn't found");
//                }
//        );
//        return trainerMapper.convertToDto(trainer);
//    }
//
//    @Override
//    @Transactional
//    public TrainerDto update(String username, @Valid TrainerDto trainerDto) {
//        LOGGER.info("Updating Trainer with username {}", username);
//        Trainer existingTrainer = trainerRepository.findByUsername(username).orElseThrow(
//                () -> new EntityNotFoundException("Trainer with username " + username + " wasn't found")
//        );
//
//        String trainingTypeName = trainerDto.getSpecialization().getTrainingTypeName();
//
//        TrainingType trainingType = trainingTypeRepository.findByName(trainingTypeName).orElseThrow(
//                () -> new EntityNotFoundException("TrainingType with name " + trainingTypeName + " wasn't found")
//        );
//
//        existingTrainer.setSpecialization(trainingType);
//        existingTrainer.getUser().setFirstName(trainerDto.getUser().getFirstName());
//        existingTrainer.getUser().setLastName(trainerDto.getUser().getLastName());
//        existingTrainer.getUser().setUsername(userNameGeneratorService.generateUniqueUsername(trainerDto.getUser()));
//        existingTrainer.getUser().setPassword(passwordEncoder.encode(trainerDto.getUser().getPassword()));
//        existingTrainer.setSpecialization(trainingTypeMapper.convertToEntity(trainerDto.getSpecialization()));
//
//        Trainer updatedTrainer = trainerRepository.save(existingTrainer);
//        LOGGER.info("Trainer with username {} updated successfully", updatedTrainer.getUser().getUsername());
//
//        return trainerMapper.convertToDto(updatedTrainer);
//    }
//
//    @Override
//    public boolean authenticateTrainer(String userName, String password) {
//        Trainer existingTrainer = trainerRepository.findByUserName(userName).orElseThrow(
//                () -> new EntityNotFoundException("TrainingType with username " + username + " wasn't found")
//        );
//        return passwordEncoder.matches(password, existingTrainer.getUser().getPassword());
//    }
//
//    @Override
//    @Transactional
//    public void changeStatus(String username, Boolean isActive) {
//        Trainer existingTrainer = trainerRepository.findByUsername(username).orElseThrow(
//                () -> new EntityNotFoundException("Trainer with username " + username + " wasn't found")
//        );
//        existingTrainer.getUser().setIsActive(isActive);
//    }
//
//    @Override
//    @Transactional
//    public void changePassword(String username, String lastPassword, String newPassword) {
//        Trainer existingTrainer = trainerRepository.findByUsername(username).orElseThrow(
//                () -> new EntityNotFoundException("Trainee with username " + username + " wasn't found")
//        );
//        if (passwordEncoder.matches(lastPassword, existingTrainer.getUser().getPassword())) {
//            existingTrainer.getUser().setPassword(newPassword);
//            trainerRepository.save(existingTrainer);
//        } else {
//            throw new IllegalArgumentException("Wrong password");
//        }
//    }
//
//    @Override
//    public List<TrainerDto> getUnassignedTrainersList(String traineeUsername) {
//        Trainee existingTrainee = traineeRepository.findByUsername(traineeUsername).orElseThrow(
//                () -> new EntityNotFoundException("Trainee with username " + traineeUsername + " wasn't found")
//        );
//        List<Trainer> trainers = trainerRepository.findAll();
//
//        return trainers.stream()
//                .filter(trainer -> !trainer.getTrainees().contains(existingTrainee))
//                .map(trainerMapper::convertToDto)
//                .toList();
//    }
//
//    @Override
//    @Transactional
//    public List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames) {
//        Trainee existingTrainee = traineeRepository.findByUsername(traineeUsername).orElseThrow(
//                () -> new EntityNotFoundException("Trainee with username " + traineeUsername + " wasn't found")
//        );
//
//        List<Trainer> trainers = trainersUsernames.stream()
//                .map(trainerRepository::findByUsername)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .toList();
//
//        existingTrainee.setTrainers(trainers);
//        traineeRepository.save(existingTrainee);
//
//        return trainers.stream()
//                .map(trainerMapper::convertToDto)
//                .toList();
//    }
}
