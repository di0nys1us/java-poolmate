package land.eies.poolmate.mutator;

import graphql.schema.DataFetchingEnvironment;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.graphql.annotation.GraphQLMutator;
import land.eies.graphql.mutator.Mutator;
import land.eies.graphql.validation.ValidationException;
import land.eies.poolmate.domain.User;
import land.eies.poolmate.mutator.validation.Password;
import land.eies.poolmate.mutator.validation.UniqueEmail;
import land.eies.poolmate.repository.UserRepository;
import land.eies.poolmate.schema.Schema;

@GraphQLMutator(bindings = {
        @GraphQLFieldBinding(fieldName = "createUser", parentType = Schema.MUTATION_TYPE_NAME)
})
@Transactional
public class CreateUserMutator implements Mutator<CreateUserMutator.CreateUserOutput> {

    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public CreateUserMutator(final ObjectMapper objectMapper,
                             final Validator validator,
                             final PasswordEncoder passwordEncoder,
                             final UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public CreateUserOutput get(final DataFetchingEnvironment environment) {
        final CreateUserInput input = objectMapper.convertValue(environment.getArgument("input"), CreateUserInput.class);

        final Set<ConstraintViolation<CreateUserInput>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

        return new CreateUserOutput(
                userRepository.save(
                        User.builder()
                                .firstName(input.getFirstName())
                                .lastName(input.getLastName())
                                .email(input.getEmail())
                                .hashedPassword(passwordEncoder.encode(input.getPassword()))
                                .administrator(input.getAdministrator())
                                .build()
                )
        );
    }

    static class CreateUserInput {

        private final String firstName;
        private final String lastName;
        private final String email;
        private final String password;
        private final Boolean administrator;

        @JsonCreator
        public CreateUserInput(@JsonProperty("firstName") final String firstName,
                               @JsonProperty("lastName") final String lastName,
                               @JsonProperty("email") final String email,
                               @JsonProperty("password") final String password,
                               @JsonProperty("administrator") final Boolean administrator) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.administrator = administrator;
        }

        @NotEmpty
        public String getFirstName() {
            return firstName;
        }

        @NotEmpty
        public String getLastName() {
            return lastName;
        }

        @UniqueEmail
        public String getEmail() {
            return email;
        }

        @Password
        public String getPassword() {
            return password;
        }

        @NotNull
        public Boolean getAdministrator() {
            return administrator;
        }
    }

    static class CreateUserOutput {

        private final User user;

        public CreateUserOutput(final User user) {
            this.user = user;
        }

        @JsonProperty("user")
        public User getUser() {
            return user;
        }
    }
}
