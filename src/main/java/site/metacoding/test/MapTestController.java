package site.metacoding.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapTestController {

    @GetMapping("/test/map")
    public String testMap() {
        return "/test/map";
    }
}
