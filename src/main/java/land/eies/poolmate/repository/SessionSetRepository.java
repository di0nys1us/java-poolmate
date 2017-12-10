package land.eies.poolmate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import land.eies.graphql.annotation.GraphQLRepository;
import land.eies.poolmate.domain.SessionSet;

@GraphQLRepository
public interface SessionSetRepository extends JpaRepository<SessionSet, Long> {

    List<SessionSet> findBySessionId(final Long sessionId);
}
