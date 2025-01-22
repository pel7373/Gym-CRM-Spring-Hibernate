package org.gym.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.dto.TrainerDto;
import org.gym.dto.TrainerDto;
import org.gym.entity.Trainee;
import org.gym.entity.Trainer;
import org.gym.entity.TrainingType;
import org.gym.exception.EntityNotFoundException;
import org.gym.exception.NullEntityException;
import org.gym.mapper.TrainerMapper;
import org.gym.mapper.TrainerMapper;
import org.gym.mapper.TrainingTypeMapper;
import org.gym.repository.TraineeRepository;
import org.gym.repository.TrainerRepository;
import org.gym.repository.TrainerRepository;
import org.gym.repository.TrainingTypeRepository;
import org.gym.service.PasswordGeneratorService;
import org.gym.service.TrainerService;
import org.gym.service.UserNameGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final UserNameGeneratorService userNameGeneratorService;
    private final PasswordGeneratorService passwordGeneratorService;
    private final TrainerMapper trainerMapper;

    @Override
    public TrainerDto select(String userName) throws EntityNotFoundException {
        return trainerMapper.convertToDto(trainerRepository.findByUserName(userName).get());
    }

    @Override
    public TrainerDto create(TrainerDto trainerDto) throws NullEntityException {
        trainerDto.getUser().setUserName(
                userNameGeneratorService.generate(
                        trainerDto.getUser().getFirstName(),
                        trainerDto.getUser().getLastName()
                ));

        Trainer trainer = trainerMapper.convertToEntity(trainerDto);
        trainer.getUser().setPassword(passwordGeneratorService.generate());
        Trainer savedTrainer = trainerRepository.save(trainer);
        LOGGER.info("create: trainer was created with userName {}", savedTrainer.getUser().getUserName());
        return trainerMapper.convertToDto(savedTrainer);
    }

    @Override
    public TrainerDto update(String userName, TrainerDto trainerDto) throws EntityNotFoundException {
        Trainer oldTrainer = trainerRepository.findByUserName(userName).get();
        if (!oldTrainer.getUser().getFirstName().equals(trainerDto.getUser().getFirstName())
                || !oldTrainer.getUser().getLastName().equals(trainerDto.getUser().getLastName())) {
            oldTrainer.getUser().setUserName(
                    userNameGeneratorService.generate(trainerDto.getUser().getFirstName(), trainerDto.getUser().getLastName()));
            oldTrainer.getUser().setFirstName(trainerDto.getUser().getFirstName());
            oldTrainer.getUser().setLastName(trainerDto.getUser().getLastName());
        }
        oldTrainer.getUser().setIsActive(trainerDto.getUser().getIsActive());

        oldTrainer.setSpecialization(trainerDto.getSpecialization());
        Trainer trainer = trainerRepository.save(oldTrainer);
        TrainerDto trainerDtoResult = trainerMapper.convertToDto(trainer);
        return trainerDtoResult;
    }

    @Override
    public TrainerDto changeSpecialization(String userName, TrainingType trainingType) {
        Trainer trainer = trainerRepository.findByUserName(userName).get();
        if (!trainer.getSpecialization().equals(trainingType)) {
            trainer.setSpecialization(trainingType);
            return trainerMapper.convertToDto(trainerRepository.save(trainer));
        }
        return trainerMapper.convertToDto(trainer);
    }

    @Override
    public boolean authenticate(String userName, String password) {
        Trainer trainer;
        try {
            trainer = trainerRepository.findByUserName(userName).get();
        } catch (EntityNotFoundException e) {
            return false;
        }

        return trainer.getUser().getPassword().equals(password);
    }

    @Override
    public TrainerDto changePassword(String userName, String newPassword) {
        Trainer trainer = trainerRepository.findByUserName(userName).get();
        trainer.getUser().setPassword(newPassword);
        return trainerMapper.convertToDto(trainerRepository.save(trainer));
    }

    @Override
    public List<TrainerDto> getUnassignedTrainersList(String traineeUserName) {
        Trainee existingTrainee = traineeRepository.findByUserName(traineeUserName).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + traineeUserName + " wasn't found")
        );
        List<Trainer> trainers = trainerRepository.findAll();

        return trainers.stream()
                .filter(trainer -> !trainer.getTrainees().contains(existingTrainee))
                .map(trainerMapper::convertToDto)
                .toList();
    }

    @Override
    public List<TrainerDto> updateTrainersList(String traineeUserName, List<String> trainersUserNames) {
        Trainee existingTrainee = traineeRepository.findByUserName(traineeUserName).orElseThrow(
                () -> new EntityNotFoundException("Trainee with userName " + traineeUserName + " wasn't found")
        );

        List<Trainer> trainers = trainersUserNames.stream()
                .map(trainerRepository::findByUserName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        existingTrainee.setTrainers(trainers);
        traineeRepository.save(existingTrainee);

        return trainers.stream()
                .map(trainerMapper::convertToDto)
                .toList();
    }

    public String getPasswordTopSecretMethod(String userName) {
        return trainerRepository.findByUserName(userName).get().getUser().getPassword();
    }
}
