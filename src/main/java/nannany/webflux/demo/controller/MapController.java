package nannany.webflux.demo.controller;

import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
public class MapController {

    private final ResourceLoader resourceLoader;

    public MapController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @GetMapping("map")
    public void downloadMap(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "image/jpg");

        StreamUtils.copy(resourceLoader.getResource("classpath:image/map.jpg").getInputStream(), response.getOutputStream());
    }
}
