package org.gym;

import lombok.extern.slf4j.Slf4j;
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
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        Trainee trainee = Trainee.builder()
                .dateOfBirth(LocalDate.of(1995, 1, 23))
                .address("Vinnitsya, Soborna str. 35, ap. 26")
                .build();

        User user = new User(null, "Maria", "Petrenko", "Maria.Petrenko", "", true, null, null);
        User user2 = new User(null, "Maria2", "Petrenko2", "Maria.Petrenko2", "", true, null, null);

        trainee.setUser(user);

        System.out.println(trainee);
        Trainee trainee2;
//                = new Trainee(LocalDate.of(1995, 1, 23),
//                "Kyiv, Soborna str. 35, ap. 26", user2);

        TraineeRepository traineeDao = context.getBean(TraineeRepository.class);
        traineeDao.save(trainee);
        //traineeDao.save(trainee2);


        User user3 = new User(null, "Ivan", "Ivanov", "Maria.Petrenko", "", true, null, null);
        User user4 = new User(null, "Petro", "Petrov", "Maria.Petrenko2", "", true, null, null);
//        Trainer trainer3 = new Trainer(null, null, user3);
//        Trainer trainer4 = new Trainer(null, null, user4);
//
//        TrainerRepository trainerDao = context.getBean(TrainerRepository.class);
//        trainerDao.save(trainer3);
//        trainerDao.save(trainer4);
    }
}
