package site.metacoding.map;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PointDto {

    private List<List<String>> points;

    public List<List<String>> toPoint(List<Item> list) {
        points = new ArrayList<>();
        for (Item item : list) {
            List<String> point = new ArrayList<>();
            point.add(item.getLatitude());
            point.add(item.getLongitude());
            points.add(point);
        }
        return points;
    }

}