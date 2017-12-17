package land.eies.poolmate.mutator.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLValidator;
import land.eies.poolmate.repository.SessionRepository;

@GraphQLValidator
public class SessionIdValidator implements ConstraintValidator<SessionId, Long> {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionIdValidator(final SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public boolean isValid(final Long value, final ConstraintValidatorContext context) {
        return sessionRepository.existsById(value);
    }
}
