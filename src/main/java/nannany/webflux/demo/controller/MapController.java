package nannany.webflux.demo.controller;

import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    private enum FileTypes {
        JPG("jpg", "jpg"), SVG("svg", "svg+xml");
        public final String extension;
        public final String contentType;

        private FileTypes(final String extension, final String contentType) {
            this.extension = extension;
            this.contentType = contentType;
        }
    }

    @GetMapping("map")
    public void downloadMap(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        String[] fileNameArray = fileName.split("\\.");
        String extension = fileNameArray[fileNameArray.length - 1];

        String contentType;

        switch (extension) {
            case "jpg":
                contentType = FileTypes.JPG.contentType;
                break;
            case "svg":
                contentType = FileTypes.SVG.contentType;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + extension);
        }

        response.setHeader("Content-Type", "image/" + contentType);

        StreamUtils.copy(resourceLoader.getResource("classpath:/static/image/" + fileName).getInputStream(), response.getOutputStream());
    }
}
