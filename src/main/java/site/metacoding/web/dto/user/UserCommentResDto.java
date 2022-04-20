package site.metacoding.web.dto.user;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.domain.comment.Comment;
import site.metacoding.domain.user.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCommentResDto {
    private Optional<User> users;
    private List<Comment> comments;
}
