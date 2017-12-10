package land.eies.poolmate.security;

import org.springframework.security.core.GrantedAuthority;

public enum SecurityRole implements GrantedAuthority {

    ADMINISTRATOR, USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
