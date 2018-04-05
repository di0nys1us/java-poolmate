package land.eies.poolmate.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import land.eies.poolmate.domain.Comment;
import land.eies.poolmate.type.CommentType;

@Component
public class CommentToCommentTypeConverter extends SelfRegisteringConverter<Comment, CommentType> {

    @Autowired
    public CommentToCommentTypeConverter(final ConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public CommentType convert(final Comment comment) {
        return CommentType.builder()
                .sessionId(comment.getSessionId().toString())
                .userId(comment.getUserId().toString())
                .dateTime(comment.getDateTime())
                .text(comment.getText())
                .build();
    }
}
