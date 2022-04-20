package site.metacoding.domain.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "SELECT * FROM category WHERE userNo = :userNo", nativeQuery = true)
    List<Comment> findByUserComments(@Param("userNo") Integer no);
}
