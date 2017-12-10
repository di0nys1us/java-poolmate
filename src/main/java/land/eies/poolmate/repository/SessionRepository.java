package land.eies.poolmate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import land.eies.graphql.annotation.GraphQLRepository;
import land.eies.poolmate.domain.Session;

@GraphQLRepository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByUserId(final Long userId);
}
