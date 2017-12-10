package land.eies.poolmate.mutator;

import graphql.schema.DataFetchingEnvironment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.graphql.annotation.GraphQLMutator;
import land.eies.graphql.mutator.Mutator;
import land.eies.poolmate.domain.Session;
import land.eies.poolmate.repository.SessionRepository;
import land.eies.poolmate.repository.UserRepository;
import land.eies.poolmate.schema.Schema;

@GraphQLMutator(bindings = {
        @GraphQLFieldBinding(fieldName = "createSession", parentType = Schema.MUTATION_TYPE_NAME)
})
public class CreateSessionMutator implements Mutator<CreateSessionMutator.CreateSessionOutput> {

    private final ObjectMapper objectMapper;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Autowired
    public CreateSessionMutator(final ObjectMapper objectMapper,
                                final SessionRepository sessionRepository,
                                final UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CreateSessionOutput get(final DataFetchingEnvironment environment) {
        final CreateSessionInput input = objectMapper.convertValue(environment.getArgument("input"), CreateSessionInput.class);

        final Session session = new Session();

        BeanUtils.copyProperties(input, session);

        return new CreateSessionOutput(sessionRepository.save(session));
    }

    public static class CreateSessionInput {

        private final Long userId;
        private final LocalDate date;
        private final Integer poolLength;
        private final Integer calories;

        @JsonCreator
        public CreateSessionInput(@JsonProperty("userId") final Long userId,
                                  @JsonProperty("date") final LocalDate date,
                                  @JsonProperty("poolLength") final Integer poolLength,
                                  @JsonProperty("calories") final Integer calories) {
            this.userId = userId;
            this.date = date;
            this.poolLength = poolLength;
            this.calories = calories;
        }

        @NotNull
        @Positive
        public Long getUserId() {
            return userId;
        }

        @NotNull
        @PastOrPresent
        public LocalDate getDate() {
            return date;
        }

        @NotNull
        @Positive
        public Integer getPoolLength() {
            return poolLength;
        }

        @NotNull
        @Positive
        public Integer getCalories() {
            return calories;
        }
    }

    public static class CreateSessionOutput {

        private final Session session;

        public CreateSessionOutput(final Session session) {
            this.session = session;
        }

        @JsonProperty("session")
        public Session getSession() {
            return session;
        }
    }
}
