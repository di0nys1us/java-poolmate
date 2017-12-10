package land.eies.graphql.validation;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import javax.validation.ConstraintViolation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanValidationException extends RuntimeException implements GraphQLError {

    private static final long serialVersionUID = 1L;

    private final Set<ConstraintViolation> violations = new HashSet<>();

    public BeanValidationException(final Set<ConstraintViolation> violations) {
        if (violations != null) {
            this.violations.addAll(violations);
        }
    }

    public Set<ConstraintViolation> getViolations() {
        return new HashSet<>(violations);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return Collections.emptyList();
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return violations.stream()
                .collect(Collectors.toMap(v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage));
    }
}
