package site.metacoding.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.comment.Comment;
import site.metacoding.domain.handler.CustomException;
import site.metacoding.domain.user.User;
import site.metacoding.service.CommentService;
import site.metacoding.web.dto.ResponseDto;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;
    private final HttpSession session;

    @DeleteMapping("/s/comment/{id}")
    public @ResponseBody ResponseDto<?> deleteById(@PathVariable Integer id) {

        // 세션의 id와 comment의 userId와 비교
        User principal = (User) session.getAttribute("principal");

        commentService.댓글삭제(id, principal);
        return new ResponseDto<>(1, "성공", null);
    }

    @PostMapping("/s/post/{postId}/comment")
    public String write(@PathVariable Integer postId, Comment comment) { // x-www-form~~
        User principal = (User) session.getAttribute("principal");

        if (principal == null) {
            throw new CustomException("로그인이 필요한 기능입니다.");
            // return "redirect:/login-form";
        }

        comment.setUser(principal);
        commentService.댓글쓰기(comment, postId);
        return "redirect:/post/" + postId;

    }
}