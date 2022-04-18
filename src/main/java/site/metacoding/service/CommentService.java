package site.metacoding.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.comment.Comment;
import site.metacoding.domain.comment.CommentRepository;
import site.metacoding.domain.post.Restaurant;
import site.metacoding.domain.post.RestaurantRepository;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final RestaurantRepository restaurantRepository;
    // 서비스 DI 하면 안됨

    @Transactional
    public void 댓글쓰기(Comment comment, Integer postId) {

        Optional<Restaurant> postOp = restaurantRepository.findById(postId);

        if (postOp.isPresent()) {
            Restaurant postEntity = postOp.get();

            comment.setRestaurant(postEntity);

        } else {
            throw new RuntimeException("없는 게시글에 댓글을 작성할 수 없습니다");
        }

        commentRepository.save(comment);
    }
}