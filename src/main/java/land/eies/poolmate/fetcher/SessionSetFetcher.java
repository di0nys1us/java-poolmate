package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.SessionSet;
import land.eies.poolmate.type.SessionSetType;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "sessionSet", parentType = "Query")
})
public class SessionSetFetcher implements DataFetcher<Optional<SessionSetType>> {

    private final ConversionService conversionService;

    @Autowired
    public SessionSetFetcher(final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Optional<SessionSetType> get(final DataFetchingEnvironment environment) {
        if (environment.containsArgument("id")) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    private SessionSetType convert(final SessionSet sessionSet) {
        return conversionService.convert(sessionSet, SessionSetType.class);
    }
}
