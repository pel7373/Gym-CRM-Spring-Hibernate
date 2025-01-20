package org.gym.validator;

import org.gym.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

@Component
public class UserDtoValidator extends EntityValidator<UserDto>  {

    Validator getValidator() {
        return Validator.forInstanceOf(UserDto.class, (u, errors) -> {
            int minNamesLength = 5;
            if (u.getFirstName() == null || u.getFirstName().isBlank()
                    || u.getFirstName().length() < minNamesLength
            ) {
                errors.rejectValue("firstName", "field.min.length", new Object[]{},
                        String.format("firstName can't be blank or less than %d chars", minNamesLength));
            } else if(!u.getFirstName().matches("^[A-Z][a-z]+$")) {
                errors.rejectValue("firstName", "field.contains.digits", new Object[]{},
                        String.format("the firstName (%s) must start with a capital letter and then contain only lowercase letters", u.getFirstName()));
            }

            if (u.getLastName() == null || u.getLastName().isBlank()
                    || u.getLastName().length() < minNamesLength
            ) {
                errors.rejectValue("lastName", "field.min.length", new Object[]{},
                        String.format("lastName can't be blank or less than %d chars", minNamesLength));
            } else if(!u.getLastName().matches("^[A-Z][a-z]+$")) {
                errors.rejectValue("lastName", "field.contains.digits", new Object[]{},
                        String.format("the lastName (%s) must start with a capital letter and then contain only lowercase letters", u.getLastName()));
            }

            if (u.getIsActive() == null) {
                errors.rejectValue("isActive", "field.Null", new Object[]{},
                        "isActive can't be null");
            }
        });
    }
}
