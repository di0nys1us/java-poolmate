package land.eies.poolmate.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.io.Serializable;
import java.time.Duration;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Persistable;

import land.eies.poolmate.support.Deletable;

@Entity
@Table(name = "session_set", schema = "poolmate")
public class SessionSet implements Serializable, Deletable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "session_set_id_gen", sequenceName = "session_set_id_seq", schema = "poolmate", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_set_id_gen")
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Column(name = "swimming_time", nullable = false)
    private Duration swimmingTime;

    @NotNull
    @Column(name = "rest_time", nullable = false)
    private Duration restTime;

    @NotNull
    @Positive
    @Column(name = "laps", nullable = false)
    private Integer laps;

    @NotNull
    @Positive
    @Column(name = "average_strokes", nullable = false)
    private Integer averageStrokes;

    @NotNull
    @Positive
    @Column(name = "speed", nullable = false)
    private Integer speed;

    @NotNull
    @Positive
    @Column(name = "efficiency_index", nullable = false)
    private Integer efficiencyIndex;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    SessionSet() {
        // Hibernate
    }

    private SessionSet(final Integer number,
                       final Duration swimmingTime,
                       final Duration restTime,
                       final Integer laps,
                       final Integer averageStrokes,
                       final Integer speed,
                       final Integer efficiencyIndex,
                       final Session session) {
        this.number = number;
        this.swimmingTime = swimmingTime;
        this.restTime = restTime;
        this.laps = laps;
        this.averageStrokes = averageStrokes;
        this.speed = speed;
        this.efficiencyIndex = efficiencyIndex;
        this.session = session;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public Duration getSwimmingTime() {
        return swimmingTime;
    }

    public void setSwimmingTime(final Duration swimmingTime) {
        this.swimmingTime = swimmingTime;
    }

    public Duration getRestTime() {
        return restTime;
    }

    public void setRestTime(final Duration restTime) {
        this.restTime = restTime;
    }

    public Integer getLaps() {
        return laps;
    }

    public void setLaps(final Integer laps) {
        this.laps = laps;
    }

    public Integer getAverageStrokes() {
        return averageStrokes;
    }

    public void setAverageStrokes(final Integer averageStrokes) {
        this.averageStrokes = averageStrokes;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(final Integer speed) {
        this.speed = speed;
    }

    public Integer getEfficiencyIndex() {
        return efficiencyIndex;
    }

    public void setEfficiencyIndex(final Integer efficiencyIndex) {
        this.efficiencyIndex = efficiencyIndex;
    }

    public Session getSession() {
        return session;
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
    public boolean isDeleted() {
        return BooleanUtils.isTrue(deleted);
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

        private Integer number;
        private Duration swimmingTime;
        private Duration restTime;
        private Integer laps;
        private Integer averageStrokes;
        private Integer speed;
        private Integer efficiencyIndex;
        private Session session;

        private Builder() {
            // No operations
        }

        public Builder number(Integer number) {
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

        public Builder laps(Integer laps) {
            this.laps = laps;
            return this;
        }

        public Builder averageStrokes(Integer averageStrokes) {
            this.averageStrokes = averageStrokes;
            return this;
        }

        public Builder speed(Integer speed) {
            this.speed = speed;
            return this;
        }

        public Builder efficiencyIndex(Integer efficiencyIndex) {
            this.efficiencyIndex = efficiencyIndex;
            return this;
        }

        public Builder session(Session session) {
            this.session = session;
            return this;
        }

        public SessionSet build() {
            return new SessionSet(
                    number,
                    swimmingTime,
                    restTime,
                    laps,
                    averageStrokes,
                    speed,
                    efficiencyIndex,
                    session
            );
        }
    }
}
