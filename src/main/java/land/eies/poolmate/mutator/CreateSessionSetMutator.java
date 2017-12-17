package land.eies.poolmate.mutator;

import graphql.schema.DataFetchingEnvironment;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.time.Duration;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.graphql.annotation.GraphQLMutator;
import land.eies.graphql.mutator.Mutator;
import land.eies.graphql.validation.ValidationException;
import land.eies.poolmate.domain.SessionSet;
import land.eies.poolmate.mutator.validation.SessionId;
import land.eies.poolmate.repository.SessionRepository;
import land.eies.poolmate.repository.SessionSetRepository;
import land.eies.poolmate.schema.Schema;

@GraphQLMutator(bindings = {
        @GraphQLFieldBinding(fieldName = "createSessionSet", parentType = Schema.MUTATION_TYPE_NAME)
})
public class CreateSessionSetMutator implements Mutator<CreateSessionSetMutator.CreateSessionSetOutput> {

    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final SessionRepository sessionRepository;
    private final SessionSetRepository sessionSetRepository;

    @Autowired
    public CreateSessionSetMutator(final ObjectMapper objectMapper,
                                   final Validator validator,
                                   final SessionRepository sessionRepository,
                                   final SessionSetRepository sessionSetRepository) {
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.sessionRepository = sessionRepository;
        this.sessionSetRepository = sessionSetRepository;
    }

    @Override
    public CreateSessionSetOutput get(final DataFetchingEnvironment environment) {
        final CreateSessionSetInput input = objectMapper.convertValue(environment.getArgument("input"), CreateSessionSetInput.class);

        final Set<ConstraintViolation<CreateSessionSetInput>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        return new CreateSessionSetOutput(
                sessionSetRepository.save(SessionSet.builder()
                        .swimmingTime(input.getSwimmingTime())
                        .restTime(input.getRestTime())
                        .laps(input.getLaps())
                        .averageStrokes(input.getAverageStrokes())
                        .speed(input.getSpeed())
                        .efficiencyIndex(input.getEfficiencyIndex())
                        .session(sessionRepository.getOne(input.getSessionId()))
                        .build())
        );
    }

    static class CreateSessionSetInput {

        private final Long sessionId;
        private final Duration swimmingTime;
        private final Duration restTime;
        private final Integer laps;
        private final Integer averageStrokes;
        private final Integer speed;
        private final Integer efficiencyIndex;

        @JsonCreator
        public CreateSessionSetInput(@JsonProperty("sessionId") final Long sessionId,
                                     @JsonProperty("swimmingTime") final Duration swimmingTime,
                                     @JsonProperty("restTime") final Duration restTime,
                                     @JsonProperty("laps") final Integer laps,
                                     @JsonProperty("averageStrokes") final Integer averageStrokes,
                                     @JsonProperty("speed") final Integer speed,
                                     @JsonProperty("efficiencyIndex") final Integer efficiencyIndex) {
            this.sessionId = sessionId;
            this.swimmingTime = swimmingTime;
            this.restTime = restTime;
            this.laps = laps;
            this.averageStrokes = averageStrokes;
            this.speed = speed;
            this.efficiencyIndex = efficiencyIndex;
        }

        @SessionId
        public Long getSessionId() {
            return sessionId;
        }

        @NotNull
        public Duration getSwimmingTime() {
            return swimmingTime;
        }

        @NotNull
        public Duration getRestTime() {
            return restTime;
        }

        @NotNull
        @Positive
        public Integer getLaps() {
            return laps;
        }

        @NotNull
        @Positive
        public Integer getAverageStrokes() {
            return averageStrokes;
        }

        @NotNull
        @Positive
        public Integer getSpeed() {
            return speed;
        }

        @NotNull
        @Positive
        public Integer getEfficiencyIndex() {
            return efficiencyIndex;
        }
    }

    static class CreateSessionSetOutput {

        private final SessionSet sessionSet;

        public CreateSessionSetOutput(final SessionSet sessionSet) {
            this.sessionSet = sessionSet;
        }

        @JsonProperty("sessionSet")
        public SessionSet getSessionSet() {
            return sessionSet;
        }
    }
}
