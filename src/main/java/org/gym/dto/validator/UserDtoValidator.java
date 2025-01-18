package org.gym.dto.validator;

import org.gym.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoValidator  {

    private Validator validator = getValidator();

    public boolean validate(UserDto userDto) {
        return !validator.validateObject(userDto).hasErrors();
    }

    public String getErrorMessage(UserDto userDto) {
        Errors errors = validator.validateObject(userDto);
        if(errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            return  allErrors.stream().map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining("; ", "[ UserDto isn't valid! Errors " + errors.getErrorCount() + ": ", " ]"));
        }
        return "";
    }

    private Validator getValidator() {
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
