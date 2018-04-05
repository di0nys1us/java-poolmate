package land.eies.poolmate.type;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListType<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    private final List<T> content;

    private ListType(final List<T> content) {
        this.content = Objects.requireNonNull(content, "content was null");
    }

    @JsonProperty("content")
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public void forEach(final Consumer<? super T> action) {
        content.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return content.spliterator();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final var other = (ListType) object;

        return Objects.equals(content, other.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "ListType{" +
                "content=" + content +
                '}';
    }

    private static final ListType EMPTY = wrap(Collections.emptyList());

    @SuppressWarnings("unchecked")
    public static <T> ListType<T> empty() {
        return (ListType<T>) EMPTY;
    }

    public static <T> ListType<T> wrap(final List<T> list) {
        return new ListType<>(list);
    }

    public static <T> ListType<T> wrap(final Stream<T> stream) {
        return wrap(stream.collect(Collectors.toList()));
    }
}
