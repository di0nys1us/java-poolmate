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
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Persistable;

import land.eies.poolmate.support.Deletable;

@Entity
@Table(name = "session", schema = "poolmate")
public class Session implements Serializable, Deletable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "session_id_gen", sequenceName = "session_id_seq", schema = "poolmate", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_id_gen")
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @NotNull
    @PastOrPresent
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Positive
    @Column(name = "pool_length", nullable = false)
    private Integer poolLength = 50;

    @NotNull
    @Positive
    @Column(name = "calories", nullable = false)
    private Integer calories;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    Session() {
        // Hibernate
    }

    private Session(final LocalDate date,
                    final Integer poolLength,
                    final Integer calories,
                    final User user) {
        this.date = date;
        this.poolLength = poolLength;
        this.calories = calories;
        this.user = user;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public Integer getPoolLength() {
        return poolLength;
    }

    public void setPoolLength(final Integer poolLength) {
        this.poolLength = poolLength;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(final Integer calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
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

        private LocalDate date;
        private Integer poolLength = 50;
        private Integer calories;
        private User user;

        private Builder() {
            // No operations
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder poolLength(Integer poolLength) {
            this.poolLength = poolLength;
            return this;
        }

        public Builder calories(Integer calories) {
            this.calories = calories;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Session build() {
            return new Session(
                    date,
                    poolLength,
                    calories,
                    user
            );
        }
    }
}
