package land.eies.poolmate.mutator.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import land.eies.poolmate.repository.UserRepository;

@Component
public class UserIdValidator implements ConstraintValidator<UserId, Long> {

    private final UserRepository userRepository;

    @Autowired
    public UserIdValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(final Long value, final ConstraintValidatorContext context) {
        return userRepository.existsById(value);
    }
}
