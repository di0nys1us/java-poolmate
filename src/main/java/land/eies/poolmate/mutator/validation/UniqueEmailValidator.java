package land.eies.poolmate.mutator.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLValidator;
import land.eies.poolmate.repository.UserRepository;

@GraphQLValidator
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(value);
    }
}
