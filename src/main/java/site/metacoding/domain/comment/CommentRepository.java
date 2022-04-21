package site.metacoding.domain.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "SELECT * FROM comment WHERE userNo = :userNo ORDER BY id DESC ", nativeQuery = true)
    List<Comment> findByUserComments(@Param("userNo") Integer userNo);
}
