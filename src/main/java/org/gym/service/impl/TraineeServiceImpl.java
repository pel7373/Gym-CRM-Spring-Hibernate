package org.gym.service.impl;

import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gym.entity.Trainee;
import org.gym.dto.TraineeDto;
import org.gym.exception.InvalidIdException;
import org.gym.exception.NullEntityException;
import org.gym.mapper.TraineeMapper;
import org.gym.repository.TraineeRepository;
import org.gym.service.PasswordGeneratorService;
import org.gym.service.TraineeService;
import org.gym.service.UserNameGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static org.gym.config.AppConfig.ID_CANT_BE_NULL_OR_NEGATIVE;

@Slf4j
@Service
@AllArgsConstructor
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final UserNameGeneratorService userNameGeneratorService;
    private final PasswordGeneratorService passwordGeneratorService;

    @Override
    public TraineeDto create(@Valid TraineeDto traineeDto) throws NullEntityException {
        traineeDto.getUser().setPassword(passwordGeneratorService.generate());
        traineeDto.getUser().setUserName(
                userNameGeneratorService.generate(
                        traineeDto.getUser().getFirstName(),
                        traineeDto.getUser().getLastName()
                ));

        Trainee trainee = traineeMapper.convertToEntity(traineeDto);
        Trainee savedTrainee = traineeRepository.save(trainee);
        LOGGER.info("Trainee was created with userName {}", savedTrainee.getUser().getUserName());
        return traineeMapper.convertToDto(savedTrainee);
    }

    @Override
    public TraineeDto select(String userName) throws EntityNotFoundException {
        return traineeMapper.convertToDto(traineeRepository.findByUserName(userName).get());
    }

    @Override
    public TraineeDto update(String userName, TraineeDto traineeDTO) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void delete(String userName) throws EntityNotFoundException {

    }

    @Override
    public void changeStatus(String userName, Boolean isActive) {

    }

    @Override
    public boolean authenticate(String userName, String password) throws EntityNotFoundException {
        return false;
    }

    @Override
    public void changePassword(String userName, String newPassword) throws EntityNotFoundException, IllegalArgumentException {

    }


//    @Override
//    public TraineeDto getById(Long id) throws InvalidIdException, EntityNotFoundException {
//        if(id == null || id < 0) {
//            throw new InvalidIdException(String.format(ID_CANT_BE_NULL_OR_NEGATIVE, "getById"));
//        }
//
//        LOGGER.info("getById is finding trainee with id {}", id);
//        return traineeMapper.convertToDto(traineeRepository.findById(id));
//    }

//    @Override
//    public TraineeDto save(TraineeDto traineeDto) throws NullEntityException {
//        if(traineeDto == null) {
//            throw new NullEntityException(String.format(ENTITY_CANT_BE_NULL, "save"));
//        }
//
//        Trainee trainee = traineeMapper.convertToEntity(traineeDto);
//        trainee.setUserName(userNameGeneratorService.generate(trainee.getFirstName(), trainee.getLastName()));
//        trainee.setPassword(passwordGeneratorService.generate());
//
//        Trainee savedTrainee = traineeRepository.save(trainee);
//        LOGGER.info("save has saved trainee {}", savedTrainee);
//        return traineeMapper.convertToDto(savedTrainee);
//    }
//
//    @Override
//    public TraineeDto update(Long id, TraineeDto traineeDto) throws InvalidIdException, NullEntityException, EntityNotFoundException {
//        if(id == null || id < 0) {
//            throw new InvalidIdException(String.format(ID_CANT_BE_NULL_OR_NEGATIVE, "update"));
//        }
//
//        if(traineeDto == null) {
//            throw new NullEntityException(String.format(ENTITY_CANT_BE_NULL, "update"));
//        }
//
//        Trainee oldTrainee = traineeRepository.findById(id);
//        Trainee trainee = traineeMapper.convertToEntity(traineeDto);
//        trainee.setUserName(userNameGeneratorService.generate(trainee.getFirstName(), trainee.getLastName()));
//        trainee.setId(id);
//        trainee.setPassword(oldTrainee.getPassword());
//        LOGGER.info("update is updating trainee with id {}", id);
//        return traineeMapper.convertToDto(traineeRepository.update(id, trainee));
//    }
//
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
