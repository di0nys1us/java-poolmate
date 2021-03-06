package land.eies.graphql;

import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import graphql.schema.TypeResolver;
import graphql.schema.idl.FieldWiringEnvironment;
import graphql.schema.idl.InterfaceWiringEnvironment;
import graphql.schema.idl.UnionWiringEnvironment;
import graphql.schema.idl.WiringFactory;

import java.util.HashMap;

import org.springframework.beans.factory.ListableBeanFactory;

import land.eies.graphql.annotation.GraphQLDataFetcher;
import land.eies.graphql.annotation.GraphQLMutator;
import land.eies.graphql.annotation.GraphQLTypeResolver;
import land.eies.graphql.mutator.Mutator;

public class GraphQLSpringWiringFactory implements WiringFactory {

    private final ListableBeanFactory listableBeanFactory;

    public GraphQLSpringWiringFactory(final ListableBeanFactory listableBeanFactory) {
        this.listableBeanFactory = listableBeanFactory;
    }

    @Override
    public boolean providesDataFetcher(final FieldWiringEnvironment environment) {
        return resolveDataFetcher(environment.getFieldDefinition().getName(), environment.getParentType().getName()) != null;
    }

    @Override
    public DataFetcher getDataFetcher(final FieldWiringEnvironment environment) {
        return resolveDataFetcher(environment.getFieldDefinition().getName(), environment.getParentType().getName());
    }

    @Override
    public boolean providesTypeResolver(final InterfaceWiringEnvironment environment) {
        return resolveTypeResolver(environment.getInterfaceTypeDefinition().getName()) != null;
    }

    @Override
    public TypeResolver getTypeResolver(final InterfaceWiringEnvironment environment) {
        return resolveTypeResolver(environment.getInterfaceTypeDefinition().getName());
    }

    @Override
    public boolean providesTypeResolver(final UnionWiringEnvironment environment) {
        return resolveTypeResolver(environment.getUnionTypeDefinition().getName()) != null;
    }

    @Override
    public TypeResolver getTypeResolver(final UnionWiringEnvironment environment) {
        return resolveTypeResolver(environment.getUnionTypeDefinition().getName());
    }

    private DataFetcher resolveDataFetcher(final String fieldName, final String parentType) {
        final var candidates = new HashMap<String, DataFetcher>();

        for (final var entry : listableBeanFactory.getBeansOfType(DataFetcher.class).entrySet()) {
            final var annotation = listableBeanFactory.findAnnotationOnBean(entry.getKey(), GraphQLDataFetcher.class);

            if (annotation == null) {
                continue;
            }

            for (final var binding : annotation.bindings()) {
                if (binding.fieldName().equals(fieldName) && binding.parentType().equals(parentType)) {
                    candidates.put(entry.getKey(), entry.getValue());
                }
            }
        }

        for (final var entry : listableBeanFactory.getBeansOfType(Mutator.class).entrySet()) {
            final var annotation = listableBeanFactory.findAnnotationOnBean(entry.getKey(), GraphQLMutator.class);

            if (annotation == null) {
                continue;
            }

            for (final var binding : annotation.bindings()) {
                if (binding.fieldName().equals(fieldName) && binding.parentType().equals(parentType)) {
                    candidates.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (candidates.size() > 1) {
            throw new GraphQLException("error");
        }

        final var iterator = candidates.values().iterator();

        return iterator.hasNext() ? iterator.next() : null;
    }

    private TypeResolver resolveTypeResolver(final String typeName) {
        final var candidates = new HashMap<String, TypeResolver>();

        for (final var entry : listableBeanFactory.getBeansOfType(TypeResolver.class).entrySet()) {
            final var annotation = listableBeanFactory.findAnnotationOnBean(entry.getKey(), GraphQLTypeResolver.class);

            if (annotation == null) {
                continue;
            }

            if (annotation.typeName().equals(typeName)) {
                candidates.put(entry.getKey(), entry.getValue());
            }
        }

        if (candidates.size() > 1) {
            throw new GraphQLException("error");
        }

        final var iterator = candidates.values().iterator();

        return iterator.hasNext() ? iterator.next() : null;
    }
}
