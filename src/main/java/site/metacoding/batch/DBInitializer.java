package site.metacoding.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import site.metacoding.batch.dto.download.JejuDto;
import site.metacoding.batch.dto.download.RestaurantDto;
import site.metacoding.domain.post.Restaurant;
import site.metacoding.domain.post.RestaurantRepository;

@Configuration
public class DBInitializer {

    // @Bean
    public CommandLineRunner initDB(RestaurantRepository restaurantRepository) {
        return (restaurant) -> {

            RestTemplate rt = new RestTemplate();

            // 여기에 옮기는게 목적
            List<Restaurant> restaurants = new ArrayList<>();

            List<String> keywords = new ArrayList<>();
            keywords.add("제주시");
            keywords.add("서귀포시");

            restaurantRepository.deleteAll();

            for (int i = 0; i < keywords.size(); i++) {
                JejuDto body1 = rt.getForObject(
                        "https://map.naver.com/v5/api/search?caller=pcweb&query=" + keywords.get(i)
                                + "음식점&type=all&searchCoord=126.03779919999974;33.44996470000005&page=1&displayCount=100&isPlaceRecommendationReplace=true&lang=ko&_json",
                        JejuDto.class);
                // System.out.println(body);
                List<RestaurantDto> lists = body1.getResult().getPlace().getList();

                for (RestaurantDto restaurantDto : lists) {
                    restaurants.add(restaurantDto.toEntity());
                }
                // restaurantRepository.saveAll(restaurants);
            }

            for (int i = 0; i < keywords.size(); i++) {
                JejuDto body1 = rt.getForObject(
                        "https://map.naver.com/v5/api/search?caller=pcweb&query=" + keywords.get(i)
                                + "음식점&type=all&searchCoord=126.03779919999974;33.44996470000005&page=2&displayCount=100&isPlaceRecommendationReplace=true&lang=ko&_json",
                        JejuDto.class);
                // System.out.println(body);
                List<RestaurantDto> lists = body1.getResult().getPlace().getList();

                for (RestaurantDto restaurantDto : lists) {
                    restaurants.add(restaurantDto.toEntity());
                }
                // restaurantRepository.saveAll(restaurants);
            }

            for (int i = 0; i < keywords.size(); i++) {
                JejuDto body1 = rt.getForObject(
                        "https://map.naver.com/v5/api/search?caller=pcweb&query=" + keywords.get(i)
                                + "양식&type=all&searchCoord=126.03779919999974;33.44996470000005&page=1&displayCount=100&isPlaceRecommendationReplace=true&lang=ko&_json",
                        JejuDto.class);
                // System.out.println(body);
                List<RestaurantDto> lists = body1.getResult().getPlace().getList();

                for (RestaurantDto restaurantDto : lists) {
                    restaurants.add(restaurantDto.toEntity());
                }
                // restaurantRepository.saveAll(restaurants);
            }

            for (int i = 0; i < keywords.size(); i++) {
                JejuDto body1 = rt.getForObject(
                        "https://map.naver.com/v5/api/search?caller=pcweb&query=" + keywords.get(i)
                                + "디저트&type=all&searchCoord=126.03779919999974;33.44996470000005&page=1&displayCount=100&isPlaceRecommendationReplace=true&lang=ko&_json",
                        JejuDto.class);
                // System.out.println(body);
                List<RestaurantDto> lists = body1.getResult().getPlace().getList();

                for (RestaurantDto restaurantDto : lists) {
                    restaurants.add(restaurantDto.toEntity());
                }
                // restaurantRepository.saveAll(restaurants);
            }

            for (int i = 0; i < keywords.size(); i++) {
                JejuDto body1 = rt.getForObject(
                        "https://map.naver.com/v5/api/search?caller=pcweb&query=" + keywords.get(i)
                                + "디저트&type=all&searchCoord=126.03779919999974;33.44996470000005&page=2&displayCount=100&isPlaceRecommendationReplace=true&lang=ko&_json",
                        JejuDto.class);
                // System.out.println(body);
                List<RestaurantDto> lists = body1.getResult().getPlace().getList();

                for (RestaurantDto restaurantDto : lists) {
                    restaurants.add(restaurantDto.toEntity());
                }
                // restaurantRepository.saveAll(restaurants);
            }

            for (int i = 0; i < keywords.size(); i++) {
                JejuDto body1 = rt.getForObject(
                        "https://map.naver.com/v5/api/search?caller=pcweb&query=" + keywords.get(i)
                                + "디저트&type=all&searchCoord=126.03779919999974;33.44996470000005&page=3&displayCount=100&isPlaceRecommendationReplace=true&lang=ko&_json",
                        JejuDto.class);
                // System.out.println(body);
                List<RestaurantDto> lists = body1.getResult().getPlace().getList();

                for (RestaurantDto restaurantDto : lists) {
                    restaurants.add(restaurantDto.toEntity());
                }
                // restaurantRepository.saveAll(restaurants);
            }
            restaurantRepository.saveAll(restaurants);
        };

    }
}
