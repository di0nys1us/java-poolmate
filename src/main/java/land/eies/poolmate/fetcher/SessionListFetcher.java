package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.Session;
import land.eies.poolmate.domain.User;
import land.eies.poolmate.repository.SessionRepository;
import land.eies.poolmate.schema.Schema;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "sessions", parentType = Schema.QUERY_TYPE_NAME),
        @GraphQLFieldBinding(fieldName = "sessions", parentType = Schema.USER_TYPE_NAME)
})
public class SessionListFetcher implements DataFetcher<List<Session>> {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionListFetcher(final SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> get(final DataFetchingEnvironment environment) {
        if (Schema.QUERY_TYPE_NAME.equals(environment.getParentType().getName())) {
            return sessionRepository.findAll();
        }

        if (Schema.USER_TYPE_NAME.equals(environment.getParentType().getName())) {
            final User user = environment.getSource();
            return sessionRepository.findByUserId(user.getId());
        }

        return Collections.emptyList();
    }
}
