package kg.airbnb.airbnb.apis;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.services.impl.ImageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@CrossOrigin
@Getter @Setter
@Tag(name = "This API for saving files {images} to amazon S3 bucket")
public class ImageApi {

    private final ImageService service;

    @PostMapping(value = {"/upload"},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> uploadFile(@RequestParam MultipartFile file) {
        return service.uploadFile(file);
    }

    @DeleteMapping("/delete")
    public Map<String, String> deleteFile(@RequestParam String fileName) {
        return service.deleteFile(fileName);
    }
}
