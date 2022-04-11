package site.metacoding.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.post.Restaurant;
import site.metacoding.service.PostService;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/post/list")
    public String search(@RequestParam(defaultValue = "") String keyword, Model model) {
        List<Restaurant> restaurants = postService.mSearch(keyword);
        // System.out.println("사이즈 : " + restaurants.size());
        model.addAttribute("Restaurant", restaurants);
        return "post/list";
    }
}
