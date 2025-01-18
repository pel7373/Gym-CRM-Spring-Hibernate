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
        Trainee trainee = traineeMapper.convertToEntity(traineeDto);
        if(!oldTrainee.getUser().getFirstName().equals(trainee.getUser().getFirstName())
                || !oldTrainee.getUser().getLastName().equals(trainee.getUser().getLastName())) {
            trainee.getUser().setUserName(
                    userNameGeneratorService.generate(trainee.getUser().getFirstName(), trainee.getUser().getLastName()));
        }
        trainee.setId(oldTrainee.getId());
        trainee.getUser().setPassword(oldTrainee.getUser().getPassword());
        LOGGER.info("update: updating trainee with userName {}", userName);
        return traineeMapper.convertToDto(traineeRepository.save(trainee));
    }

    @Override
    public void delete(String userName) throws EntityNotFoundException {
        Trainee trainee = traineeRepository.findByUserName(userName).get();

    }

    @Override
    public void changeStatus(String userName, Boolean isActive) {
        Trainee trainee = traineeRepository.findByUserName(userName).get();
        if(!trainee.getUser().getIsActive().equals(isActive)) {
            trainee.getUser().setIsActive(isActive);
            traineeRepository.save(trainee);
        }
    }

    @Override
    public boolean authenticate(String userName, String password) throws EntityNotFoundException {

        return false;
    }

    @Override
    public void changePassword(String userName, String newPassword) {

    }



//    @Override
//    public void deleteById(Long id) throws InvalidIdException {
//        if(id == null || id < 0) {
//            throw new InvalidIdException(String.format(ID_CANT_BE_NULL_OR_NEGATIVE, "delete"));
//        }
//
//        LOGGER.info("deleteById is deleting trainee with id {}", id);
//        traineeRepository.deleteById(id);
//    }
}
