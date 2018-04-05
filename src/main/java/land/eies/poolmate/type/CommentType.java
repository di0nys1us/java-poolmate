package land.eies.poolmate.type;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentType implements Serializable, Comparable<CommentType> {

    private static final long serialVersionUID = 1L;

    private final String sessionId;
    private final String userId;
    private final LocalDateTime dateTime;
    private final String text;

    private CommentType(final String sessionId,
                        final String userId,
                        final LocalDateTime dateTime,
                        final String text) {
        this.sessionId = Objects.requireNonNull(sessionId, "sessionId was null");
        this.userId = Objects.requireNonNull(userId, "userId was null");
        this.dateTime = Objects.requireNonNull(dateTime, "dateTime was null");
        this.text = Objects.requireNonNull(text, "text was null");
    }

    @JsonProperty("sessionId")
    public String getSessionId() {
        return sessionId;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("dateTime")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

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

        final var other = (CommentType) object;

        return Objects.equals(sessionId, other.sessionId)
                && Objects.equals(userId, other.userId)
                && Objects.equals(dateTime, other.dateTime);

    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId, dateTime);
    }

    @Override
    public int compareTo(final CommentType other) {
        return Comparator.comparing(CommentType::getSessionId)
                .thenComparing(CommentType::getDateTime)
                .thenComparing(CommentType::getUserId)
                .compare(this, other);
    }

    @Override
    public String toString() {
        return "CommentType{" +
                "sessionId='" + sessionId + '\'' +
                ", userId='" + userId + '\'' +
                ", dateTime=" + dateTime +
                ", text='" + text + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String sessionId;
        private String userId;
        private LocalDateTime dateTime;
        private String text;

        private Builder() {
            // No operations
        }

        public Builder sessionId(final String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public CommentType build() {
            return new CommentType(
                    sessionId,
                    userId,
                    dateTime,
                    text
            );
        }
    }
}
