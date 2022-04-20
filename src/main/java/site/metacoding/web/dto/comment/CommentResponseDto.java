package site.metacoding.web.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.domain.comment.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentResponseDto {
    private Comment comment;
    private boolean auth;

}
