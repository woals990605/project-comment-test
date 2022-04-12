package site.metacoding.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.post.Restaurant;
import site.metacoding.domain.post.RestaurantRepository;

@RequiredArgsConstructor
@Service
public class PostService {

    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> mSearch(String keyword) {
        if (keyword == null) {
            return restaurantRepository.mSearch("");
        } else {
            return restaurantRepository.mSearch(keyword);
        }
    }
}
