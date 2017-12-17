package land.eies.poolmate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Persistable;

import land.eies.poolmate.support.Deletable;

@Entity
@Table(name = "user", schema = "poolmate")
public class User implements Serializable, Deletable, Persistable<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", schema = "poolmate", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @NotEmpty
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotEmpty
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @NotNull
    @Column(name = "administrator", nullable = false)
    private Boolean administrator = false;

    User() {
        // Hibernate
    }

    private User(final String firstName,
                 final String lastName,
                 final String email,
                 final String hashedPassword,
                 final boolean administrator) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.administrator = administrator;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(final String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Boolean getAdministrator() {
        return administrator;
    }

    public void setAdministrator(final Boolean administrator) {
        this.administrator = administrator;
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

        private String firstName;
        private String lastName;
        private String email;
        private String hashedPassword;
        private boolean administrator = false;

        private Builder() {
            // No operations
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder hashedPassword(String hashedPassword) {
            this.hashedPassword = hashedPassword;
            return this;
        }

        public Builder administrator(boolean administrator) {
            this.administrator = administrator;
            return this;
        }

        public User build() {
            return new User(
                    firstName,
                    lastName,
                    email,
                    hashedPassword,
                    administrator
            );
        }
    }
}
