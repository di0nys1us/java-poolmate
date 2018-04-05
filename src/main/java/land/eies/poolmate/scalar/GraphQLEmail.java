package land.eies.poolmate.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import land.eies.graphql.annotation.GraphQLScalar;

@GraphQLScalar
public class GraphQLEmail extends AbstractFactoryBean<GraphQLScalarType> {

    @Override
    public Class<?> getObjectType() {
        return GraphQLScalarType.class;
    }

    @Override
    protected GraphQLScalarType createInstance() throws Exception {
        return new GraphQLScalarType("Email", "java.lang.String", new Coercing<String, String>() {

            private String parse(final Object input) {
                if (input instanceof CharSequence) {
                    return input.toString();
                } else if (input instanceof StringValue) {
                    return ((StringValue) input).getValue();
                } else {
                    return null;
                }
            }

            @Override
            public String serialize(final Object input) {
                final var email = parse(input);

                if (email == null) {
                    throw new CoercingSerializeException("Invalid value '" + input + "' for Email");
                }

                return email;
            }

            @Override
            public String parseValue(final Object input) {
                final var email = parse(input);

                if (email == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for Email");
                }

                return email;
            }

            @Override
            public String parseLiteral(final Object input) {
                return parse(input);
            }
        });
    }
}
