package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.Session;
import land.eies.poolmate.domain.SessionSet;
import land.eies.poolmate.repository.SessionRepository;
import land.eies.poolmate.schema.Schema;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "session", parentType = Schema.QUERY_TYPE_NAME),
        @GraphQLFieldBinding(fieldName = "session", parentType = Schema.SESSION_SET_TYPE_NAME)
})
public class SessionFetcher implements DataFetcher<Optional<Session>> {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionFetcher(final SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Optional<Session> get(final DataFetchingEnvironment environment) {
        if (environment.containsArgument("id")) {
            return sessionRepository.findById(environment.getArgument("id"));
        }

        if (Schema.SESSION_SET_TYPE_NAME.equals(environment.getParentType().getName())) {
            final SessionSet sessionSet = environment.getSource();
            return Optional.of(sessionSet.getSession());
        }

        return Optional.empty();
    }
}
