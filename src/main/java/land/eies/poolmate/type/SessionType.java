package land.eies.poolmate.type;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionType implements Serializable, Comparable<SessionType> {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final long version;
    private final boolean deleted;
    private final String userId;
    private final LocalDate date;
    private final int poolLength;
    private final int calories;
    private final NavigableSet<SessionSetType> sets;
    private final NavigableSet<CommentType> comments;

    private SessionType(final String id,
                        final long version,
                        final boolean deleted,
                        final String userId,
                        final LocalDate date,
                        final int poolLength,
                        final int calories,
                        final NavigableSet<SessionSetType> sets,
                        final NavigableSet<CommentType> comments) {
        this.id = id;
        this.version = version;
        this.deleted = deleted;
        this.userId = userId;
        this.date = date;
        this.poolLength = poolLength;
        this.calories = calories;
        this.sets = sets;
        this.comments = comments;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("version")
    public long getVersion() {
        return version;
    }

    @JsonProperty("deleted")
    public boolean isDeleted() {
        return deleted;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("date")
    public LocalDate getDate() {
        return date;
    }

    @JsonProperty("poolLength")
    public int getPoolLength() {
        return poolLength;
    }

    @JsonProperty("calories")
    public int getCalories() {
        return calories;
    }

    @JsonProperty("sets")
    public NavigableSet<SessionSetType> getSets() {
        return Collections.unmodifiableNavigableSet(sets);
    }

    @JsonProperty("comments")
    public NavigableSet<CommentType> getComments() {
        return Collections.unmodifiableNavigableSet(comments);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final var other = (SessionType) object;

        return Objects.equals(this.id, other.id)
                && Objects.equals(this.date, other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public int compareTo(final SessionType other) {
        return Comparator.comparing(SessionType::getDate)
                .thenComparing(SessionType::getId)
                .compare(this, other);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private long version;
        private boolean deleted;
        private String userId;
        private LocalDate date;
        private int poolLength;
        private int calories;
        private NavigableSet<SessionSetType> sets = new TreeSet<>();
        private NavigableSet<CommentType> comments = new TreeSet<>();

        private Builder() {
            // No operations
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder version(long version) {
            this.version = version;
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder poolLength(int poolLength) {
            this.poolLength = poolLength;
            return this;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder sets(final NavigableSet<SessionSetType> sets) {
            this.sets = sets;
            return this;
        }

        public Builder comments(final NavigableSet<CommentType> comments) {
            this.comments = comments;
            return this;
        }

        public SessionType build() {
            return new SessionType(
                    id,
                    version,
                    deleted,
                    userId,
                    date,
                    poolLength,
                    calories,
                    sets,
                    comments
            );
        }
    }
}
