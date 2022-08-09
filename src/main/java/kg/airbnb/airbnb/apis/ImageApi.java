package kg.airbnb.airbnb.apis;

import kg.airbnb.airbnb.services.impl.ImageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@CrossOrigin
@Getter @Setter
public class ImageApi {

    private final ImageService service;

    @PostMapping(value = {"/upload"},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }
}
