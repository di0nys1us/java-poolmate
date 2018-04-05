package land.eies.poolmate.domain.support;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import land.eies.poolmate.domain.SessionSet;

class JsonTypeTest {

    private JsonType jsonType;

    @BeforeEach
    void setUp() {
        final Properties parameters = new Properties();
        parameters.setProperty(JsonType.CANONICAL, "java.util.List<land.eies.poolmate.domain.SessionSet>");

        jsonType = new JsonType();
        jsonType.setParameterValues(parameters);
    }

    @Test
    void deepCopy() {
        final List<SessionSet> sets = Collections.singletonList(
                SessionSet.builder()
                        .number(1)
                        .swimmingTime(Duration.ofSeconds(120L))
                        .restTime(Duration.ofSeconds(60L))
                        .laps(2)
                        .averageStrokes(20)
                        .speed(100)
                        .efficiencyIndex(45)
                        .build()
        );

        final Object copy = jsonType.deepCopy(sets);

        System.out.println(sets);
    }
}
