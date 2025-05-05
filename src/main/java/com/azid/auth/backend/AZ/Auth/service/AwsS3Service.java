package com.azid.auth.backend.AZ.Auth.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.demo.model.UploadS3Respond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.bucket.rootFolder}")
    private String rootFolder;

    public AwsS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public UploadS3Respond uploadFileToS3(String folderName, List<MultipartFile> files) {
        String folderKey = folderName.endsWith("/") ? rootFolder+folderName : rootFolder+folderName + "/";

        UploadS3Respond uploadS3Respond = new UploadS3Respond();
        // Check if the folder already exists
        if (!amazonS3.doesObjectExist(bucketName, folderKey)) {
            amazonS3.putObject(bucketName,folderKey,"");
            for (MultipartFile file : files) {
                try {
                    String filekey = folderKey + file.getOriginalFilename();

                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentType(file.getContentType());
                    objectMetadata.setContentLength(file.getSize());

                    //insert file insto s3 folder
                    log.info("Uploading file to S3: {}", file.getOriginalFilename());
                    amazonS3.putObject(bucketName, filekey, file.getInputStream(), objectMetadata);
                    uploadS3Respond.setStatus("200");
                    uploadS3Respond.setMessage("Successfully uploaded file to S3");
                    uploadS3Respond.setFolderName(folderName);

                } catch (Exception e) {
                    uploadS3Respond.setStatus("404");
                    uploadS3Respond.setMessage("Failed uploaded file to S3");
                    uploadS3Respond.setFolderName(folderName);
                }
            }
        }else {
            log.info("Folder already exists in S3: {}", folderName);
            uploadS3Respond.setStatus("404");
            uploadS3Respond.setMessage("Failed uploaded file to S3");
            uploadS3Respond.setFolderName(folderName);
        }


        return uploadS3Respond;
    }

    public List<String> listFilesByPolicyId(String policyID,String folderPath) {
        String prefix = rootFolder.concat(folderPath);

        ListObjectsV2Request listReq = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix(prefix);

        ListObjectsV2Result listRes = amazonS3.listObjectsV2(listReq);

        List<String> fileNames = new ArrayList<>();

        for (S3ObjectSummary objectSummary : listRes.getObjectSummaries()) {
            // Skip "folder" itself (empty object ending with /)
            if (!objectSummary.getKey().endsWith("/")) {
                // example : https://s3.ap-southeast-1.amazonaws.com/myapp.nz/claim-policy/123454_muhamad_nazhim/birthcertificate.txt
                //String fileUrl = amazonS3.getUrl(bucketName, objectSummary.getKey()).toString();
                //fileNames.add(fileUrl);

                // example : claim-policy/123454_muhamad_nazhim/birthcertificate.txt
                 fileNames.add(objectSummary.getKey());
            }
        }

        return fileNames;
    }

    public S3ObjectInputStream downloadFile(String keyName) {
        S3Object s3Object = amazonS3.getObject(bucketName, keyName);
        return s3Object.getObjectContent(); // This is an InputStream
    }
}
