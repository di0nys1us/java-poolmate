package land.eies.poolmate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import land.eies.poolmate.domain.Comment;
import land.eies.poolmate.domain.Session;
import land.eies.poolmate.domain.SessionSet;
import land.eies.poolmate.repository.SessionRepository;

@SpringBootApplication
public class PoolmateApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PoolmateApplication.class, args);
    }

    @Bean
    public CommandLineRunner testData(final SessionRepository sessionRepository) {
        final var sets = new TreeSet<SessionSet>();
        final var comments = new TreeSet<Comment>();

        sets.addAll(
                Arrays.asList(
                        SessionSet.builder()
                                .number(1)
                                .swimmingTime(Duration.ofSeconds(120L))
                                .restTime(Duration.ofSeconds(60L))
                                .laps(2)
                                .averageStrokes(20)
                                .speed(100)
                                .efficiencyIndex(45)
                                .build(),
                        SessionSet.builder()
                                .number(2)
                                .swimmingTime(Duration.ofSeconds(220L))
                                .restTime(Duration.ofSeconds(160L))
                                .laps(6)
                                .averageStrokes(22)
                                .speed(110)
                                .efficiencyIndex(40)
                                .build()
                )
        );

        comments.addAll(
                Collections.singletonList(
                        Comment.builder()
                                .dateTime(LocalDateTime.now())
                                .text("This is a comment.")
                                .userId(UUID.randomUUID())
                                .build()
                )
        );

        return args -> {
            sessionRepository.save(
                    Session.builder()
                            .userId(UUID.randomUUID())
                            .date(LocalDate.now())
                            .poolLength(50)
                            .calories(550)
                            .sets(sets)
                            .comments(comments)
                            .build()
            );
        };
    }
}
