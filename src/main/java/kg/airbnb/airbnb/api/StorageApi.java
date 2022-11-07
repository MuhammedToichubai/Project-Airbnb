package kg.airbnb.airbnb.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.services.impl.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/files")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "S3 API", description = "This API for saving files {images} to amazon S3 bucket")
public class StorageApi {

    private final ImageService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> uploadFile(@RequestParam MultipartFile file) {
        return service.uploadFile(file);
    }

    @DeleteMapping
    public Map<String, String> deleteFile(@RequestParam String fileName) {
        return service.deleteFile(fileName);
    }

}
