package org.gym;

import org.gym.dao.TraineeDao;
import org.gym.dao.TrainerDao;
import org.gym.entity.Trainee;
import org.gym.entity.Trainer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

@Configuration
@ComponentScan("org.gym")
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        context.registerShutdownHook();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                context.close();
            }});

        Trainee trainee = new Trainee();
        TraineeDao traineeDao = context.getBean(TraineeDao.class);
        traineeDao.save(trainee);

        Trainer trainer = new Trainer();
        TrainerDao trainerDao = context.getBean(TrainerDao.class);
        trainerDao.save(trainer);
    }
}
