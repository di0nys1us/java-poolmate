package land.eies.poolmate.mutator.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UserIdValidator.class})
@NotNull
@Positive
public @interface SessionId {

    String message() default "{land.eies.poolmate.mutator.validation.SessionId.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
