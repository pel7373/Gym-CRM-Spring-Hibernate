package org.gym.service.impl;

import org.gym.exception.EntityNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.entity.Trainee;
import org.gym.dto.TraineeDto;
import org.gym.exception.NullEntityException;
import org.gym.mapper.TraineeMapper;
import org.gym.repository.TraineeRepository;
import org.gym.service.PasswordGeneratorService;
import org.gym.service.TraineeService;
import org.gym.service.UserNameGeneratorService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final UserNameGeneratorService userNameGeneratorService;
    private final PasswordGeneratorService passwordGeneratorService;

    @Override
    public TraineeDto select(String userName) throws EntityNotFoundException {
        return traineeMapper.convertToDto(traineeRepository.findByUserName(userName).get());
    }

    @Override
    public TraineeDto create(TraineeDto traineeDto) throws NullEntityException {
        traineeDto.getUser().setUserName(
                userNameGeneratorService.generate(
                        traineeDto.getUser().getFirstName(),
                        traineeDto.getUser().getLastName()
                ));

        Trainee trainee = traineeMapper.convertToEntity(traineeDto);
        trainee.getUser().setPassword(passwordGeneratorService.generate());
        Trainee savedTrainee = traineeRepository.save(trainee);
        LOGGER.info("create: trainee was created with userName {}", savedTrainee.getUser().getUserName());
        return traineeMapper.convertToDto(savedTrainee);
    }

    @Override
    public TraineeDto update(String userName, TraineeDto traineeDto) throws EntityNotFoundException {
        Trainee oldTrainee = traineeRepository.findByUserName(userName).get();
        if(!oldTrainee.getUser().getFirstName().equals(traineeDto.getUser().getFirstName())
                || !oldTrainee.getUser().getLastName().equals(traineeDto.getUser().getLastName())) {
            oldTrainee.getUser().setUserName(
                    userNameGeneratorService.generate(traineeDto.getUser().getFirstName(), traineeDto.getUser().getLastName()));
            oldTrainee.getUser().setFirstName(traineeDto.getUser().getFirstName());
            oldTrainee.getUser().setLastName(traineeDto.getUser().getLastName());
        }
        oldTrainee.getUser().setIsActive(traineeDto.getUser().getIsActive());
        oldTrainee.setDateOfBirth(traineeDto.getDateOfBirth());
        oldTrainee.setAddress(traineeDto.getAddress());
        LOGGER.info("update: updating trainee with userName {}", userName);
        return traineeMapper.convertToDto(traineeRepository.save(oldTrainee));
    }

    @Override
    public void delete(String userName) throws EntityNotFoundException {
        traineeRepository.delete(userName);
    }

    @Override
    public TraineeDto changeStatus(String userName, Boolean isActive) {
        Trainee trainee = traineeRepository.findByUserName(userName).get();
        if(!trainee.getUser().getIsActive().equals(isActive)) {
            trainee.getUser().setIsActive(isActive);
            return traineeMapper.convertToDto(traineeRepository.save(trainee));
        }
        return traineeMapper.convertToDto(trainee);
    }

    @Override
    public boolean authenticate(String userName, String password) {
        Trainee trainee;
        try {
            trainee = traineeRepository.findByUserName(userName).get();
        } catch (EntityNotFoundException e) {
            return false;
        }

        return trainee.getUser().getPassword().equals(password);
    }

    @Override
    public TraineeDto changePassword(String userName, String newPassword) {
        Trainee trainee = traineeRepository.findByUserName(userName).get();
        trainee.getUser().setPassword(newPassword);
        return traineeMapper.convertToDto(traineeRepository.save(trainee));
    }
}
