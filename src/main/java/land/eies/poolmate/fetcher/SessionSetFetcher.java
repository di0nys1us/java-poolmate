package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.SessionSet;
import land.eies.poolmate.repository.SessionSetRepository;
import land.eies.poolmate.schema.Schema;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "sessionSet", parentType = Schema.QUERY_TYPE_NAME)
})
public class SessionSetFetcher implements DataFetcher<Optional<SessionSet>> {

    private final SessionSetRepository sessionSetRepository;

    @Autowired
    public SessionSetFetcher(final SessionSetRepository sessionSetRepository) {
        this.sessionSetRepository = sessionSetRepository;
    }

    @Override
    public Optional<SessionSet> get(final DataFetchingEnvironment environment) {
        if (environment.containsArgument("id")) {
            return sessionSetRepository.findById(environment.getArgument("id"));
        }

        return Optional.empty();
    }
}
