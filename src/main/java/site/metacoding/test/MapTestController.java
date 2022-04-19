package site.metacoding.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.post.Restaurant;
import site.metacoding.domain.post.RestaurantRepository;

@RequiredArgsConstructor
@Controller
public class MapTestController {
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/test/map/{id}")
    public String testMap(Model model, @PathVariable Integer id) {
        // Sample s1 = new Sample(126.3101556, 33.4629867, "애월은전복");
        Restaurant restaurantEntity = restaurantRepository.findById(id).get();
        model.addAttribute("restaurant", restaurantEntity);

        return "/test/map";
    }
}
