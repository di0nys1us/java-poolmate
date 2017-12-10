package land.eies.poolmate.scalar;


import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.time.Duration;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import land.eies.graphql.annotation.GraphQLScalar;

@GraphQLScalar
public class GraphQLDuration extends AbstractFactoryBean<GraphQLScalarType> {

    @Override
    public Class<?> getObjectType() {
        return GraphQLScalarType.class;
    }

    @Override
    protected GraphQLScalarType createInstance() throws Exception {
        return new GraphQLScalarType("Duration", "java.time.Duration", new Coercing<Duration, Duration>() {

            private Duration parse(final Object input) {
                if (input instanceof Duration) {
                    return (Duration) input;
                } else if (input instanceof CharSequence) {
                    return Duration.parse((CharSequence) input);
                } else if (input instanceof StringValue) {
                    return Duration.parse(((StringValue) input).getValue());
                } else {
                    return null;
                }
            }

            @Override
            public Duration serialize(final Object input) {
                final Duration duration = parse(input);

                if (duration == null) {
                    throw new CoercingSerializeException("Invalid value '" + input + "' for Duration");
                }

                return duration;
            }

            @Override
            public Duration parseValue(final Object input) {
                final Duration duration = parse(input);

                if (duration == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for Duration");
                }

                return duration;
            }

            @Override
            public Duration parseLiteral(final Object input) {
                return parse(input);
            }
        });
    }
}
