package kg.airbnb.airbnb.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Value("${prefix-for-file-link}")
    private String prefixForFileLink;

    private final AmazonS3 s3Client;


    public String uploadFile(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.addUserMetadata("Content-Type", file.getContentType());
            metadata.addUserMetadata("Content-Length", String.valueOf(file.getSize()));
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prefixForFileLink + fileName;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }
}
