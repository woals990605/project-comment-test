package site.metacoding.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.comment.Comment;
import site.metacoding.domain.post.Restaurant;
import site.metacoding.domain.user.User;
import site.metacoding.service.PostService;
import site.metacoding.web.dto.CommentResponseDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final HttpSession session;

    @GetMapping({ "/", "/post/list" })
    public String search(@RequestParam(defaultValue = "") String keyword, Model model) {
        List<Restaurant> restaurants = postService.mSearch(keyword);
        // System.out.println("사이즈 : " + restaurants.size());
        model.addAttribute("Restaurant", restaurants);
        model.addAttribute("keyword", keyword);
        return "post/search";
    }

    @GetMapping("/post/{id}") // 인증이 필요없기 때문에 주소는 이대로 놔둬야 한다. 따라서 주소는 놔두고 Get요청시에는 필터에서 /post 거르는거를 제외를 시켜준다.
    public String detail(@PathVariable Integer id, Model model) {

        Restaurant postEntity = postService.글상세보기(id);

        // // 게시불이 없으면 error 페이지로 이동
        // if (postEntity == null) {
        // return "error/page1";
        // }
        User principal = (User) session.getAttribute("principal");

        List<CommentResponseDto> comments = new ArrayList<>();

        System.out.println("comments : " + comments);

        for (Comment comment : postEntity.getComments()) {

            CommentResponseDto dto = new CommentResponseDto();
            dto.setComment(comment);

            if (principal != null) { // 인증
                if (principal.getId() == comment.getUser().getId()) { // 권한
                    dto.setAuth(true);
                } else {
                    dto.setAuth(false);
                }
            }

            comments.add(dto);
        }

        model.addAttribute("Restaurant", postEntity);
        model.addAttribute("comments", comments);
        return "post/detail";

    }

}
