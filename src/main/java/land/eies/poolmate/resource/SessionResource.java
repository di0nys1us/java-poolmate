package land.eies.poolmate.resource;

import land.eies.poolmate.model.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@RestController("sessions")
public class SessionResource {

    @GetMapping
    public ResponseEntity<Collection<Session>> getSessions() {
        final Session session = new Session();
        session.setId(1L);
        session.setDate(LocalDate.now());

        final Collection<Session> sessions = new ArrayList<>();
        sessions.add(session);

        return ResponseEntity.ok(sessions);
    }
}
