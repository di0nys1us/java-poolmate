package land.eies.poolmate.converter;

import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import land.eies.poolmate.domain.Comment;
import land.eies.poolmate.domain.Session;
import land.eies.poolmate.domain.SessionSet;
import land.eies.poolmate.type.CommentType;
import land.eies.poolmate.type.SessionSetType;
import land.eies.poolmate.type.SessionType;

@Component
public class SessionToSessionTypeConverter extends SelfRegisteringConverter<Session, SessionType> {

    @Autowired
    public SessionToSessionTypeConverter(final ConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public SessionType convert(final Session session) {
        return SessionType.builder()
                .id(session.getId().toString())
                .version(session.getVersion())
                .deleted(session.isDeleted())
                .userId(session.getUserId().toString())
                .date(session.getDate())
                .poolLength(session.getPoolLength())
                .calories(session.getCalories())
                .sets(session.getSets()
                        .stream()
                        .map(this::convert)
                        .collect(Collectors.toCollection(TreeSet::new)))
                .comments(session.getComments()
                        .stream()
                        .map(this::convert)
                        .collect(Collectors.toCollection(TreeSet::new)))
                .build();
    }

    private SessionSetType convert(final SessionSet sessionSet) {
        return getConversionService().convert(sessionSet, SessionSetType.class);
    }

    private CommentType convert(final Comment comment) {
        return getConversionService().convert(comment, CommentType.class);
    }
}
