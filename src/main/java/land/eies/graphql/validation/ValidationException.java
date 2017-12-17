package land.eies.graphql.validation;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphQLException;
import graphql.language.SourceLocation;

import javax.validation.ConstraintViolation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationException extends GraphQLException implements GraphQLError {

    private static final long serialVersionUID = 1L;

    private final Set<ConstraintViolation> violations = new HashSet<>();

    public <T> ValidationException(final Set<ConstraintViolation<T>> violations) {
        super("ValidationException");
        if (violations != null) {
            this.violations.addAll(violations);
        }
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
        return Collections.singletonMap(
                "fieldErrors",
                violations.stream()
                        .collect(Collectors.toMap(v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage))
        );
    }
}
