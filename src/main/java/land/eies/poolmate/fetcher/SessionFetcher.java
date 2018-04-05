package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.Session;
import land.eies.poolmate.repository.SessionRepository;
import land.eies.poolmate.type.SessionType;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "session", parentType = "Query"),
        @GraphQLFieldBinding(fieldName = "session", parentType = "SessionSet")
})
public class SessionFetcher implements DataFetcher<Optional<SessionType>> {

    private final ConversionService conversionService;
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionFetcher(final ConversionService conversionService,
                          final SessionRepository sessionRepository) {
        this.conversionService = conversionService;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Optional<SessionType> get(final DataFetchingEnvironment environment) {
        if (environment.containsArgument("id")) {
            return sessionRepository.findById(FetcherSupport.getId(environment))
                    .map(this::convert);
        }

        if ("SessionSet".equals(environment.getParentType().getName())) {
            final var sessionSet = environment.getSource();
            return Optional.empty();
        }

        return Optional.empty();
    }

    private SessionType convert(final Session session) {
        return conversionService.convert(session, SessionType.class);
    }
}
