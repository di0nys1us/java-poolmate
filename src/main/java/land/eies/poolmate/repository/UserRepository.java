package land.eies.poolmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import land.eies.graphql.annotation.GraphQLRepository;
import land.eies.poolmate.domain.User;

@GraphQLRepository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(final String email);
}
