package org.gym.mapper;

import org.gym.dto.TraineeDto;
import org.gym.entity.Trainee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TraineeMapper {
    TraineeDto convertToDto(Trainee trainee);
    Trainee convertToEntity(TraineeDto traineeDto) ;
}
