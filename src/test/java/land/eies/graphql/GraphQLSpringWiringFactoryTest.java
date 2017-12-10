package land.eies.graphql;

import graphql.language.FieldDefinition;
import graphql.language.ObjectTypeDefinition;
import graphql.schema.idl.FieldWiringEnvironment;
import graphql.schema.idl.WiringFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@DisplayName("GraphQLSpringWiringFactoryTest")
class GraphQLSpringWiringFactoryTest {

    @Autowired
    private WiringFactory wiringFactory;

    @Nested
    @DisplayName("DataFetcher")
    class DataFetcher {

        @Test
        void providesDataFetcher() {
            wiringFactory.providesDataFetcher(fieldWiringEnvironment("user", "Query"));
        }
    }

    static FieldWiringEnvironment fieldWiringEnvironment(final String fieldName, final String parentType) {
        final FieldWiringEnvironment fieldWiringEnvironment = Mockito.mock(FieldWiringEnvironment.class);
        Mockito.when(fieldWiringEnvironment.getFieldDefinition()).thenReturn(new FieldDefinition(fieldName));
        Mockito.when(fieldWiringEnvironment.getParentType()).thenReturn(new ObjectTypeDefinition(parentType));

        return fieldWiringEnvironment;
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        WiringFactory wiringFactory(final ListableBeanFactory listableBeanFactory) {
            return new GraphQLSpringWiringFactory(listableBeanFactory);
        }
    }
}
