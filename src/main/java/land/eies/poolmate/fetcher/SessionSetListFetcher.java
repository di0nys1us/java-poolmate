package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.Session;
import land.eies.poolmate.domain.SessionSet;
import land.eies.poolmate.repository.SessionSetRepository;
import land.eies.poolmate.schema.Schema;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "sessionSets", parentType = Schema.QUERY_TYPE_NAME),
        @GraphQLFieldBinding(fieldName = "sessionSets", parentType = Schema.SESSION_TYPE_NAME)
})
public class SessionSetListFetcher implements DataFetcher<List<SessionSet>> {

    private final SessionSetRepository sessionSetRepository;

    @Autowired
    public SessionSetListFetcher(final SessionSetRepository sessionSetRepository) {
        this.sessionSetRepository = sessionSetRepository;
    }

    @Override
    public List<SessionSet> get(final DataFetchingEnvironment environment) {
        if (Schema.QUERY_TYPE_NAME.equals(environment.getParentType().getName())) {
            return sessionSetRepository.findAll();
        }

        if (Schema.SESSION_TYPE_NAME.equals(environment.getParentType().getName())) {
            final Session session = environment.getSource();
            return sessionSetRepository.findBySessionId(session.getId());
        }

        return Collections.emptyList();
    }
}
