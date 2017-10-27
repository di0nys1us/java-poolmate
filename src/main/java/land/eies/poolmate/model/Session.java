package land.eies.poolmate.model;

import java.time.LocalDate;

public class Session {

    private final Long id;
    private final LocalDate date;

    public Session(final Long id, final LocalDate date) {
        this.id = id;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }
}
