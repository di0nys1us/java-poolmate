package land.eies.poolmate.type;

import java.io.Serializable;
import java.time.Duration;
import java.util.Comparator;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionSetType implements Serializable, Comparable<SessionSetType> {

    private static final long serialVersionUID = 1L;

    private final String sessionId;
    private final int number;
    private final Duration swimmingTime;
    private final Duration restTime;
    private final int laps;
    private final int averageStrokes;
    private final int speed;
    private final int efficiencyIndex;

    private SessionSetType(final String sessionId,
                           final int number,
                           final Duration swimmingTime,
                           final Duration restTime,
                           final int laps,
                           final int averageStrokes,
                           final int speed,
                           final int efficiencyIndex) {
        this.sessionId = Objects.requireNonNull(sessionId, "sessionId was null");
        this.number = number;
        this.swimmingTime = Objects.requireNonNull(swimmingTime, "swimmingTime was null");
        this.restTime = Objects.requireNonNull(restTime, "restTime was null");
        this.laps = laps;
        this.averageStrokes = averageStrokes;
        this.speed = speed;
        this.efficiencyIndex = efficiencyIndex;
    }

    @JsonProperty("sessionId")
    public String getSessionId() {
        return sessionId;
    }

    @JsonProperty("number")
    public int getNumber() {
        return number;
    }

    @JsonProperty("swimmingTime")
    public Duration getSwimmingTime() {
        return swimmingTime;
    }

    @JsonProperty("restTime")
    public Duration getRestTime() {
        return restTime;
    }

    @JsonProperty("laps")
    public int getLaps() {
        return laps;
    }

    @JsonProperty("averageStrokes")
    public int getAverageStrokes() {
        return averageStrokes;
    }

    @JsonProperty("speed")
    public int getSpeed() {
        return speed;
    }

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

        final var other = (SessionSetType) object;

        return Objects.equals(this.sessionId, other.sessionId)
                && number == other.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, number);
    }

    @Override
    public int compareTo(final SessionSetType other) {
        return Comparator.comparing(SessionSetType::getSessionId)
                .thenComparing(SessionSetType::getNumber)
                .compare(this, other);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String sessionId;
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

        public Builder sessionId(final String sessionId) {
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

        public SessionSetType build() {
            return new SessionSetType(
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
