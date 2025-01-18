package org.gym.mapper;

import org.gym.Main;
import org.gym.dto.TrainerDto;
import org.gym.dto.TrainingTypeDto;
import org.gym.dto.UserDto;
import org.gym.entity.Trainer;
import org.gym.entity.TrainingType;
import org.gym.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class TrainerMapperTest {

//    private UserMapper userMapper = new UserMapperImpl();
//    private TrainerMapper trainerMapper = new TrainerMapperImpl();

    private static TrainerMapper trainerMapper;
    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void setUp() {
        context = new AnnotationConfigApplicationContext(Main.class);
        trainerMapper = context.getBean(TrainerMapper.class);
    }

    @Test
    void convertToDto() {
        Trainer trainer = Trainer.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .userName("FirstName.LastName")
                        .password("password")
                        .isActive(true)
                        .build())
                .specialization(TrainingType.builder().id(2L).trainingTypeName("yoga").build())
                .build();

        TrainerDto trainerDto = trainerMapper.convertToDto(trainer);

        assertNotNull(trainerDto);
        assertEquals(trainer.getUser().getFirstName(), trainerDto.getUser().getFirstName());
        assertEquals(trainer.getUser().getLastName(), trainerDto.getUser().getLastName());
        assertEquals(trainer.getUser().getUserName(), trainerDto.getUser().getUserName());
        assertEquals(trainer.getUser().getIsActive(), trainerDto.getUser().getIsActive());
        assertEquals(trainer.getSpecialization().getTrainingTypeName(), trainerDto.getSpecialization().getTrainingTypeName());
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TrainerDto trainerDto = trainerMapper.convertToDto(null);
        assertNull(trainerDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        TrainerDto trainerDto = TrainerDto.builder()
                .user(UserDto.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .userName("FirstName.LastName")
                        .isActive(true)
                        .build())
                .specialization(TrainingTypeDto.builder()
                        .trainingTypeName("yoga").build())
                .build();

        Trainer trainer = trainerMapper.convertToEntity(trainerDto);

        assertNotNull(trainer);
        assertEquals(trainerDto.getUser().getFirstName(), trainer.getUser().getFirstName());
        assertEquals(trainerDto.getUser().getLastName(), trainer.getUser().getLastName());
        assertEquals(trainerDto.getUser().getUserName(), trainer.getUser().getUserName());
        assertEquals(trainerDto.getUser().getIsActive(), trainer.getUser().getIsActive());
        assertEquals(trainerDto.getSpecialization().getTrainingTypeName(), trainer.getSpecialization().getTrainingTypeName());
    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        Trainer trainer = trainerMapper.convertToEntity(null);
        assertNull(trainer, "Expected convertToEntity to return null when input is null");
    }
}
