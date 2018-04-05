package land.eies.poolmate.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLFieldBinding;
import land.eies.poolmate.domain.SessionSet;
import land.eies.poolmate.type.ListType;
import land.eies.poolmate.type.SessionSetType;

@GraphQLDataFetcher(bindings = {
        @GraphQLFieldBinding(fieldName = "sessionSets", parentType = "Query"),
        @GraphQLFieldBinding(fieldName = "sessionSets", parentType = "Session")
})
public class SessionSetListFetcher implements DataFetcher<ListType<SessionSetType>> {

    private final ConversionService conversionService;

    @Autowired
    public SessionSetListFetcher(final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ListType<SessionSetType> get(final DataFetchingEnvironment environment) {
        if ("Query".equals(environment.getParentType().getName())) {
            return ListType.empty();
        }

        return ListType.empty();
    }

    private SessionSetType convert(final SessionSet sessionSet) {
        return conversionService.convert(sessionSet, SessionSetType.class);
    }
}
