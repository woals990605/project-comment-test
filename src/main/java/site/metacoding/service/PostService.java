package site.metacoding.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.post.Restaurant;
import site.metacoding.domain.post.RestaurantRepository;

@RequiredArgsConstructor
@Service
public class PostService {

    private final RestaurantRepository restaurantRepository;

    public Page<Restaurant> mSearch(String keyword, Integer page) {
        PageRequest pr = PageRequest.of(page, 20, Sort.by(Direction.DESC, "id"));
        if (keyword.equals("")) {
            return restaurantRepository.findAll(pr);
        } else {
            return restaurantRepository.mSearch(keyword, pr);
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
