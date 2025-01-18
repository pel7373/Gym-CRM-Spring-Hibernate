package org.gym;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.gym.dto.TraineeDto;
import org.gym.dto.UserDto;
import org.gym.facade.TraineeFacade;
import org.gym.repository.TraineeRepository;
import org.gym.repository.TrainerRepository;
import org.gym.entity.Trainee;
import org.gym.entity.Trainer;
import org.gym.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

@Slf4j
@ComponentScan(basePackages = "org.gym")
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        UserDto userDto = new UserDto("Maria", "Petrenko", "Maria.Petrenko", "", true);
        UserDto userDto2 = new UserDto("Pa", "Pa", "Maria.Petrenko2", "", null);

        User user = new User(null, "Ma", "Petrenko", "Maria.Petrenko", "", null);
        User user2 = new User(null, "Pa", "Petrenko2", "Maria.Petrenko2", "", null);

        TraineeDto traineeDto = TraineeDto.builder()
                .user(userDto)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        TraineeDto traineeDto2
                = new TraineeDto(userDto2, LocalDate.of(1995, 1, 23),
                "Kyiv, Soborna str. 35, ap. 26");

        Trainee trainee = Trainee.builder()
                .user(user)
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        Trainee trainee2
                = new Trainee(null, LocalDate.of(1995, 1, 23),
                "Kyiv, Soborna str. 35, ap. 26", user, null, null);

        TraineeFacade traineeFacade = context.getBean(TraineeFacade.class);
        //TraineeRepository traineeRepository = context.getBean(TraineeRepository.class);
        //traineeRepository.save(trainee);
        //System.out.println(traineeRepository.save(trainee));
        System.out.println(traineeDto);
        TraineeDto savedTraineeDto = traineeFacade.create(traineeDto);
        System.out.println(savedTraineeDto);
        System.out.println(traineeDto2);
        TraineeDto savedTraineeDto2 = traineeFacade.create(traineeDto2);
        System.out.println(savedTraineeDto2);
//        String userName = savedTraineeDto.getUser().getUserName();
//        System.out.println("savedTrainee userName: " + userName);

//        TraineeDto traineeDto1 = null;
//        traineeFacade.create(traineeDto1);
        //Trainee trainee = traineeRepository.findByUserName(userName).get();
        //TraineeDto traineeDto55 = traineeFacade.select(userName, null);

//        String password = traineeDto55.getUser().getPassword();
//        TraineeDto traineeDtoAgain = traineeFacade.select(userName, password);
//        System.out.println(traineeDtoAgain);

        //traineeFacade.create(traineeDto2);
        //traineeDao.save(trainee2);


//        User user3 = new User(null, "Ivan", "Ivanov", "Maria.Petrenko", "", true, null, null);
//        User user4 = new User(null, "Petro", "Petrov", "Maria.Petrenko2", "", true, null, null);
//        Trainer trainer3 = new Trainer(null, null, user3);
//        Trainer trainer4 = new Trainer(null, null, user4);
//
//        TrainerRepository trainerDao = context.getBean(TrainerRepository.class);
//        trainerDao.save(trainer3);
//        trainerDao.save(trainer4);
    }
}
