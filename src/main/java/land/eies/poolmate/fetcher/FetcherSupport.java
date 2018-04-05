package land.eies.poolmate.fetcher;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

import java.util.UUID;

public final class FetcherSupport {

    private FetcherSupport() {
        // No operations
    }

    public static UUID getId(final DataFetchingEnvironment environment) {
        if (environment.containsArgument("id")) {
            return UUID.fromString(environment.getArgument("id"));
        }

        throw new GraphQLException("UUID not present");
    }
}
