package land.eies.poolmate.resource;

import graphql.GraphQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import land.eies.graphql.ExecutionInputSupplier;

@RestController
@RequestMapping("graphql")
public class GraphQLResource {

    private final GraphQL graphQL;

    @Autowired
    public GraphQLResource(final GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @GetMapping
    public ResponseEntity get(final ExecutionInputSupplier executionInputSupplier) {
        final var result = graphQL.execute(executionInputSupplier.get());
        return ResponseEntity.ok(result.toSpecification());
    }

    @PostMapping
    public ResponseEntity post(@RequestBody final ExecutionInputSupplier executionInputSupplier) {
        final var result = graphQL.execute(executionInputSupplier.get());
        return ResponseEntity.ok(result.toSpecification());
    }
}
