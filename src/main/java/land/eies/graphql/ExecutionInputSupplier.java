package land.eies.graphql;

import graphql.ExecutionInput;

import java.util.Map;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutionInputSupplier implements Supplier<ExecutionInput> {

    private final String query;
    private final String operationName;
    private final Object context;
    private final Object root;
    private final Map<String, Object> variables;

    @JsonCreator
    public ExecutionInputSupplier(@JsonProperty("query") final String query,
                                  @JsonProperty("operationName") final String operationName,
                                  @JsonProperty("context") final Object context,
                                  @JsonProperty("root") final Object root,
                                  @JsonProperty("variables") final Map<String, Object> variables) {
        this.query = query;
        this.operationName = operationName;
        this.context = context;
        this.root = root;
        this.variables = variables;
    }

    @Override
    public ExecutionInput get() {
        return ExecutionInput.newExecutionInput()
                .query(this.query == null ? "" : this.query)
                .operationName(this.operationName)
                .context(this.context)
                .root(this.root)
                .variables(this.variables)
                .build();
    }
}
