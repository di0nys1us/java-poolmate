package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.User;
import land.eies.poolmate.repository.UserRepository;
import land.eies.poolmate.schema.Schema;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "users", parentType = Schema.QUERY_TYPE_NAME)
})
public class UserListFetcher implements DataFetcher<List<User>> {

    private final UserRepository userRepository;

    @Autowired
    public UserListFetcher(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> get(final DataFetchingEnvironment environment) {
        return userRepository.findAll();
    }
}
