package site.metacoding.batch.dto.download;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.domain.post.Restaurant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private String thumUrl; // 썸네일
    private String name; // 가게 이름
    private String address; // 주소
    private String telDisplay; // 전화번호
    private String bizhourInfo; // 영업시간
    private String homePage; // 홈페이지
    private List<String> category; // 카테고리
    private String menuInfo; // 메뉴
    private String x; // 위도
    private String y; // 경도

    public Restaurant toEntity() {
        Restaurant restaurant = new Restaurant();
        restaurant.setThumUrl(thumUrl);
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setTelDisplay(telDisplay);
        restaurant.setBizhourInfo(bizhourInfo);
        restaurant.setHomePage(homePage);
        restaurant.setMenuInfo(menuInfo);
        restaurant.setX(x);
        restaurant.setY(y);

        String categoryGubun = "";
        for (String temp : category) {
            categoryGubun += temp + ";";
        }

        restaurant.setCategory(categoryGubun);

        return restaurant;
    }
}