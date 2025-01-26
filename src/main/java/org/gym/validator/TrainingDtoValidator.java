package org.gym.validator;

import org.gym.dto.TrainingDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

@Component
public class TrainingDtoValidator extends EntityValidator<TrainingDto> {

    @Override
    Validator getValidator() {
        return Validator.forInstanceOf(TrainingDto.class, (t, errors) -> {
            int minNamesLength = 5;
            int minDuration = 15;
            if (t.getTrainingName() == null) {
                errors.rejectValue("trainingName", "field.min.length", new Object[]{},
                        String.format("the trainingName can't be blank or less than %d chars", minNamesLength));
            }

            if (t.getDate() == null) {
                errors.rejectValue("date", "field.date.notNull", new Object[]{},
                        "the trainingDate can't be null");
            }

            if (t.getDuration() == null || t.getDuration() < minDuration) {
                errors.rejectValue("duration", "field.duration.notNull.length", new Object[]{},
                        String.format("the trainingDuration can't be null or less than %d", minDuration));
            }
        });
    }
}
