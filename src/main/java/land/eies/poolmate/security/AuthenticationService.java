package land.eies.poolmate.security;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import land.eies.poolmate.domain.User;
import land.eies.poolmate.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username = '" + username + "' not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getHashedPassword(),
                user.getAdministrator() ? EnumSet.of(SecurityRole.USER, SecurityRole.ADMINISTRATOR) : EnumSet.of(SecurityRole.USER)
        );
    }
}
