package land.eies.poolmate.mutator.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLValidator;
import land.eies.poolmate.repository.UserRepository;

@GraphQLValidator
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
