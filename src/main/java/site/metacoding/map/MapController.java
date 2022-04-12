package site.metacoding.map;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MapController {

    private final MapRepository repository;

    @GetMapping("/loading")
    public @ResponseBody List<List<String>> load() {

        List<Item> list = repository.findAll();
        PointDto pointDto = new PointDto();

        List<List<String>> points = pointDto.toPoint(list);

        return points;

    }
}