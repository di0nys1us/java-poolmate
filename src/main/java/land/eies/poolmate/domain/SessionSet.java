package land.eies.poolmate.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.io.Serializable;
import java.time.Duration;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionSet implements Serializable, Comparable<SessionSet> {

    private static final long serialVersionUID = 1L;

    private final UUID sessionId;
    private final int number;
    private final Duration swimmingTime;
    private final Duration restTime;
    private final int laps;
    private final int averageStrokes;
    private final int speed;
    private final int efficiencyIndex;

    @JsonCreator
    SessionSet(@JsonProperty("sessionId") final UUID sessionId,
               @JsonProperty("number") final int number,
               @JsonProperty("swimmingTime") final Duration swimmingTime,
               @JsonProperty("restTime") final Duration restTime,
               @JsonProperty("laps") final int laps,
               @JsonProperty("averageStrokes") final int averageStrokes,
               @JsonProperty("speed") final int speed,
               @JsonProperty("efficiencyIndex") final int efficiencyIndex) {
        this.sessionId = Objects.requireNonNull(sessionId, "sessionId was null");
        this.number = number;
        this.swimmingTime = Objects.requireNonNull(swimmingTime, "swimmingTime was null");
        this.restTime = Objects.requireNonNull(restTime, "restTime was null");
        this.laps = laps;
        this.averageStrokes = averageStrokes;
        this.speed = speed;
        this.efficiencyIndex = efficiencyIndex;
    }

    @NotNull
    @JsonProperty("sessionId")
    public UUID getSessionId() {
        return sessionId;
    }

    @Positive
    @JsonProperty("number")
    public int getNumber() {
        return number;
    }

    @NotNull
    @JsonProperty("swimmingTime")
    public Duration getSwimmingTime() {
        return swimmingTime;
    }

    @NotNull
    @JsonProperty("restTime")
    public Duration getRestTime() {
        return restTime;
    }

    @Positive
    @JsonProperty("laps")
    public int getLaps() {
        return laps;
    }

    @Positive
    @JsonProperty("averageStrokes")
    public int getAverageStrokes() {
        return averageStrokes;
    }

    @Positive
    @JsonProperty("speed")
    public int getSpeed() {
        return speed;
    }

    @Positive
    @JsonProperty("efficiencyIndex")
    public int getEfficiencyIndex() {
        return efficiencyIndex;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final var other = (SessionSet) object;

        return Objects.equals(this.sessionId, other.sessionId)
                && number == other.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, number);
    }

    @Override
    public int compareTo(final SessionSet other) {
        return Comparator.comparing(SessionSet::getSessionId)
                .thenComparing(SessionSet::getNumber)
                .compare(this, other);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private UUID sessionId;
        private int number;
        private Duration swimmingTime;
        private Duration restTime;
        private int laps;
        private int averageStrokes;
        private int speed;
        private int efficiencyIndex;

        private Builder() {
            // No operations
        }

        public Builder sessionId(final UUID sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public Builder swimmingTime(Duration swimmingTime) {
            this.swimmingTime = swimmingTime;
            return this;
        }

        public Builder restTime(Duration restTime) {
            this.restTime = restTime;
            return this;
        }

        public Builder laps(int laps) {
            this.laps = laps;
            return this;
        }

        public Builder averageStrokes(int averageStrokes) {
            this.averageStrokes = averageStrokes;
            return this;
        }

        public Builder speed(int speed) {
            this.speed = speed;
            return this;
        }

        public Builder efficiencyIndex(int efficiencyIndex) {
            this.efficiencyIndex = efficiencyIndex;
            return this;
        }

        public SessionSet build() {
            return new SessionSet(
                    sessionId,
                    number,
                    swimmingTime,
                    restTime,
                    laps,
                    averageStrokes,
                    speed,
                    efficiencyIndex
            );
        }
    }
}
