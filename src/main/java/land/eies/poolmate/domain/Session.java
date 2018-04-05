package land.eies.poolmate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;
import java.util.UUID;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;

import land.eies.poolmate.domain.support.JsonType;
import land.eies.poolmate.support.Deletable;

@Entity
@Table(name = "session", schema = "poolmate")
public class Session implements Serializable, Deletable, Persistable<UUID> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "pool_length", nullable = false)
    private int poolLength = 50;

    @Column(name = "calories", nullable = false)
    private int calories;

    @Column(name = "sets")
    @Type(type = "land.eies.poolmate.domain.support.JsonType", parameters = {
            @Parameter(
                    name = JsonType.CANONICAL,
                    value = "java.util.NavigableSet<land.eies.poolmate.domain.SessionSet>"
            )
    })
    private NavigableSet<SessionSet> sets;

    @Column(name = "comments")
    @Type(type = "land.eies.poolmate.domain.support.JsonType", parameters = {
            @Parameter(
                    name = JsonType.CANONICAL,
                    value = "java.util.NavigableSet<land.eies.poolmate.domain.Comment>"
            )
    })
    private NavigableSet<Comment> comments;

    @SuppressWarnings("unused")
    Session() {
        // JPA
    }

    private Session(final UUID userId,
                    final LocalDate date,
                    final int poolLength,
                    final int calories,
                    final NavigableSet<SessionSet> sets,
                    final NavigableSet<Comment> comments) {
        this.userId = Objects.requireNonNull(userId, "userId was null");
        this.date = Objects.requireNonNull(date, "date was null");
        this.poolLength = poolLength;
        this.calories = calories;
        this.sets = Objects.requireNonNull(sets, "sets was null");
        this.comments = Objects.requireNonNull(comments, "comments was null");
    }

    @Override
    public UUID getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @NotNull
    public UUID getUserId() {
        return userId;
    }

    @NotNull
    @PastOrPresent
    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    @Positive
    public int getPoolLength() {
        return poolLength;
    }

    public void setPoolLength(final int poolLength) {
        this.poolLength = poolLength;
    }

    @Positive
    public int getCalories() {
        return calories;
    }

    public void setCalories(final int calories) {
        this.calories = calories;
    }

    @NotNull
    public NavigableSet<SessionSet> getSets() {
        return Collections.unmodifiableNavigableSet(sets);
    }

    public void setSets(final NavigableSet<SessionSet> sets) {
        this.sets = sets;
    }

    @NotNull
    public NavigableSet<Comment> getComments() {
        return Collections.unmodifiableNavigableSet(comments);
    }

    public void setComments(final NavigableSet<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public void delete() {
        deleted = true;
    }

    @Override
    public void recover() {
        deleted = false;
    }

    @Transient
    @Override
    public boolean isNew() {
        return id == null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private UUID userId;
        private LocalDate date;
        private int poolLength = 50;
        private int calories;
        private NavigableSet<SessionSet> sets = new TreeSet<>();
        private NavigableSet<Comment> comments = new TreeSet<>();

        private Builder() {
            // No operations
        }

        public Builder userId(UUID userId) {
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

        public Builder sets(final NavigableSet<SessionSet> sets) {
            this.sets = sets;
            return this;
        }

        public Builder comments(final NavigableSet<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Session build() {
            return new Session(
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
