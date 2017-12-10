package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.Session;
import land.eies.poolmate.domain.User;
import land.eies.poolmate.repository.UserRepository;
import land.eies.poolmate.schema.Schema;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "user", parentType = Schema.QUERY_TYPE_NAME),
        @GraphQLFieldBinding(fieldName = "user", parentType = Schema.SESSION_TYPE_NAME)
})
public class UserFetcher implements DataFetcher<Optional<User>> {

    private final UserRepository userRepository;

    @Autowired
    public UserFetcher(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> get(final DataFetchingEnvironment environment) {
        if (environment.containsArgument("id")) {
            return userRepository.findById(environment.getArgument("id"));
        }

        if (environment.containsArgument("email")) {
            return userRepository.findByEmail(environment.getArgument("email"));
        }

        if (Schema.SESSION_TYPE_NAME.equals(environment.getParentType().getName())) {
            final Session session = environment.getSource();
            return Optional.of(session.getUser());
        }

        return Optional.empty();
    }
}
