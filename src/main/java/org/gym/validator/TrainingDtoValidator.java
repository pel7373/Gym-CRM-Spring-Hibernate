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
            if (t.getTrainingName() == null || t.getTrainingName().isBlank()
                    || t.getTrainingName().length() < minNamesLength
            ) {
                errors.rejectValue("TrainingName", "field.min.length", new Object[]{},
                        String.format("the trainingName can't be blank or less than %d chars", minNamesLength));
            } else if(!t.getTrainingName().matches("^[A-Za-z.:0-9]+[\\sA-Za-z.:0-9]*$")) {
                errors.rejectValue("trainingName", "field.must.contain.letters-digits-dot", new Object[]{},
                        String.format("the trainingName (%s) must contain letter, digits, spaces, dots and colons",
                                t.getTrainingName()));
            }

            if (t.getDate() == null) {
                errors.rejectValue("trainingDate", "field.trainingDate.notNull", new Object[]{},
                        "the trainingDate can't be null");
            }

            if (t.getDuration() == null || t.getDuration() < minDuration) {
                errors.rejectValue("trainingDuration", "field.traininDate.notNull.length", new Object[]{},
                        String.format("the trainingDuration can't be null or less than %d", minDuration));
            }
        });
    }
}
