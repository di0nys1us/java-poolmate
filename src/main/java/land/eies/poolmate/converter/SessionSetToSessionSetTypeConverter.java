package land.eies.poolmate.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import land.eies.poolmate.domain.SessionSet;
import land.eies.poolmate.type.SessionSetType;

@Component
public class SessionSetToSessionSetTypeConverter extends SelfRegisteringConverter<SessionSet, SessionSetType> {

    @Autowired
    public SessionSetToSessionSetTypeConverter(final ConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public SessionSetType convert(final SessionSet sessionSet) {
        return SessionSetType.builder()
                .sessionId(sessionSet.getSessionId().toString())
                .number(sessionSet.getNumber())
                .swimmingTime(sessionSet.getSwimmingTime())
                .restTime(sessionSet.getRestTime())
                .laps(sessionSet.getLaps())
                .averageStrokes(sessionSet.getAverageStrokes())
                .speed(sessionSet.getSpeed())
                .efficiencyIndex(sessionSet.getEfficiencyIndex())
                .build();
    }
}
