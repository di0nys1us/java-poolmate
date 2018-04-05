package land.eies.poolmate.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment implements Serializable, Comparable<Comment> {

    private static final long serialVersionUID = 1L;

    private final UUID sessionId;
    private final UUID userId;
    private final LocalDateTime dateTime;
    private final String text;

    @JsonCreator
    Comment(@JsonProperty("sessionId") final UUID sessionId,
            @JsonProperty("userId") final UUID userId,
            @JsonProperty("dateTime") final LocalDateTime dateTime,
            @JsonProperty("text") final String text) {
        this.sessionId = Objects.requireNonNull(sessionId, "sessionId was null");
        this.userId = Objects.requireNonNull(userId, "userId was null");
        this.dateTime = Objects.requireNonNull(dateTime, "dateTime was null");
        this.text = Objects.requireNonNull(text, "text was null");
    }

    @NotNull
    @JsonProperty("sessionId")
    public UUID getSessionId() {
        return sessionId;
    }

    @NotNull
    @JsonProperty("userId")
    public UUID getUserId() {
        return userId;
    }

    @NotNull
    @PastOrPresent
    @JsonProperty("dateTime")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @NotBlank
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final var other = (Comment) object;

        return Objects.equals(this.dateTime, other.dateTime)
                && Objects.equals(this.userId, other.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, userId);
    }

    @Override
    public int compareTo(final Comment other) {
        return Comparator.comparing(Comment::getSessionId)
                .thenComparing(Comment::getDateTime)
                .thenComparing(Comment::getUserId)
                .compare(this, other);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "sessionId=" + sessionId +
                ", userId=" + userId +
                ", dateTime=" + dateTime +
                ", text='" + text + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private UUID sessionId;
        private UUID userId;
        private LocalDateTime dateTime;
        private String text;

        private Builder() {
            // No operations
        }

        public Builder sessionId(final UUID sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder userId(final UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder dateTime(final LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder text(final String text) {
            this.text = text;
            return this;
        }

        public Comment build() {
            return new Comment(
                    sessionId,
                    userId,
                    dateTime,
                    text
            );
        }
    }
}
