package site.metacoding.service;

import java.util.List;
import java.util.Optional;

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

    public Restaurant 글상세보기(Integer id) {
        Optional<Restaurant> postOp = restaurantRepository.findById(id);

        if (postOp.isPresent()) {
            Restaurant postEntity = postOp.get();
            return postEntity;
        } else {
            throw new RuntimeException("해당 게시글을 찾을 수 없습니다.");
        }

    }
}
