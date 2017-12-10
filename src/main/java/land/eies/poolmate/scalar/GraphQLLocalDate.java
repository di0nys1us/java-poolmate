package land.eies.poolmate.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.time.LocalDate;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import land.eies.graphql.annotation.GraphQLScalar;

@GraphQLScalar
public class GraphQLLocalDate extends AbstractFactoryBean<GraphQLScalarType> {

    @Override
    public Class<?> getObjectType() {
        return GraphQLScalarType.class;
    }

    @Override
    protected GraphQLScalarType createInstance() throws Exception {
        return new GraphQLScalarType("LocalDate", "java.time.LocalDate", new Coercing<LocalDate, LocalDate>() {

            private LocalDate parse(final Object input) {
                if (input instanceof LocalDate) {
                    return (LocalDate) input;
                } else if (input instanceof CharSequence) {
                    return LocalDate.parse((CharSequence) input);
                } else if (input instanceof StringValue) {
                    return LocalDate.parse(((StringValue) input).getValue());
                } else {
                    return null;
                }
            }

            @Override
            public LocalDate serialize(final Object input) {
                final LocalDate localDate = parse(input);

                if (localDate == null) {
                    throw new CoercingSerializeException("Invalid value '" + input + "' for LocalDate");
                }

                return localDate;
            }

            @Override
            public LocalDate parseValue(final Object input) {
                final LocalDate localDate = parse(input);

                if (localDate == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for LocalDate");
                }

                return localDate;
            }

            @Override
            public LocalDate parseLiteral(final Object input) {
                return parse(input);
            }
        });
    }
}
