package land.eies.poolmate.configuration;

import graphql.GraphQL;
import graphql.GraphQLException;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.IOException;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import land.eies.graphql.GraphQLSpringWiringFactory;
import land.eies.graphql.annotation.GraphQLScalar;

@Configuration
public class GraphQLSpringConfiguration {

    @Bean
    public TypeDefinitionRegistry typeDefinitionRegistry(@Value("classpath:/graphql/**/*.graphql") final Resource[] resources) {
        final var parser = new SchemaParser();
        final var registry = new TypeDefinitionRegistry();

        for (final var resource : resources) {
            try {
                registry.merge(parser.parse(resource.getFile()));
            } catch (IOException e) {
                throw new GraphQLException(e);
            }
        }

        return registry;
    }

    @Bean
    public RuntimeWiring runtimeWiring(final ListableBeanFactory listableBeanFactory) {
        final var builder = RuntimeWiring.newRuntimeWiring()
                .wiringFactory(new GraphQLSpringWiringFactory(listableBeanFactory));

        for (final var entry : listableBeanFactory.getBeansOfType(GraphQLScalarType.class).entrySet()) {
            final var annotation = listableBeanFactory.findAnnotationOnBean(entry.getKey(), GraphQLScalar.class);

            if (annotation == null) {
                continue;
            }

            builder.scalar(entry.getValue());
        }

        return builder.build();
    }

    @Bean
    public GraphQLSchema graphQLSchema(final TypeDefinitionRegistry typeDefinitionRegistry, final RuntimeWiring runtimeWiring) {
        return new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    @Bean
    public GraphQL graphQL(final GraphQLSchema graphQLSchema) {
        return GraphQL.newGraphQL(graphQLSchema)
                .build();
    }
}
