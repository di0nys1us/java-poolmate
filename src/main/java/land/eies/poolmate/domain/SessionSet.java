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

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "swimming_time", nullable = false)
    private Duration swimmingTime;

    @Column(name = "rest_time", nullable = false)
    private Duration restTime;

    @Column(name = "laps", nullable = false)
    private Integer laps;

    @Column(name = "average_strokes", nullable = false)
    private Integer averageStrokes;

    @Column(name = "speed", nullable = false)
    private Integer speed;

    @Column(name = "efficiency_index", nullable = false)
    private Integer efficiencyIndex;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Override
    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(final Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getNumber() {
        return number;
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

    public void setSession(final Session session) {
        this.session = session;
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
}
