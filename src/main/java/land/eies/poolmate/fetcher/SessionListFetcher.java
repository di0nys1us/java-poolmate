package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.Session;
import land.eies.poolmate.repository.SessionRepository;
import land.eies.poolmate.type.ListType;
import land.eies.poolmate.type.SessionType;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "sessions", parentType = "Query")
})
public class SessionListFetcher implements DataFetcher<ListType<SessionType>> {

    private final ConversionService conversionService;
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionListFetcher(final ConversionService conversionService,
                              final SessionRepository sessionRepository) {
        this.conversionService = conversionService;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public ListType<SessionType> get(final DataFetchingEnvironment environment) {
        return ListType.wrap(
                sessionRepository.findAll()
                        .stream()
                        .map(this::convert)
        );
    }

    private SessionType convert(final Session session) {
        return conversionService.convert(session, SessionType.class);
    }
}
