package com.neo.neomarket.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class S3FileUploadService {

    private final AmazonS3Client amazonS3Client;

    // 여러 파일을 S3에 업로드합니다.
    public List<String> uploadFiles(List<MultipartFile> multipartFiles, String bucketName) throws IOException {
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            fileUrls.add(amazonS3Client.getUrl(bucketName, fileName).toString());
        }

        return fileUrls;
    }

    // 각 파일에 대해 고유한 파일 이름을 생성합니다.
    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    //  업로드된 각 파일의 URL을 반환합니다.
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
        }
    }
}