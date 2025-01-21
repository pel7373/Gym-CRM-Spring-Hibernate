package org.gym.validator;

import org.gym.dto.TrainingDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

@Component
public class TrainingDtoValidator extends EntityValidator<TrainingDto> {
    Validator getValidator() {
        return Validator.forInstanceOf(TrainingDto.class, (t, errors) -> {
            int minNamesLength = 5;
            int minDuration = 15;
            if (t.getTrainingName() == null || t.getTrainingName().isBlank()
                    || t.getTrainingName().length() < minNamesLength
            ) {
                errors.rejectValue("TrainingName", "field.min.length", new Object[]{},
                        String.format("the trainingName can't be blank or less than %d chars", minNamesLength));
            } else if(!t.getTrainingName().matches("^[A-Z][a-z.:0-9]+$")) {
                errors.rejectValue("trainingName", "field.must.contain.letters-digits-dot", new Object[]{},
                        String.format("the trainingName (%s) must start with a capital letter and then contain only lowercase letters, dots and colons",
                                t.getTrainingName()));
            }

            if (t.getDate() == null) {
                errors.rejectValue("traininDate", "field.traininDate.notNull", new Object[]{},
                        "the traininDate can't be null");
            }

            if (t.getDuration() == null || t.getDuration() < minDuration) {
                errors.rejectValue("trainingDuration", "field.traininDate.notNull.length", new Object[]{},
                        String.format("the trainingDuration can't be null or less than %d", minDuration));
            }
        });
    }
}
