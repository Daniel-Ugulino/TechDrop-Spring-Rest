package br.edu.infnet.TechStore.model.service;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3fileService {

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${aws.s3.bucket}")
    private String s3bucket;

    public String uploadFile(String filePath, MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try {
            FileOutputStream outputStream = new FileOutputStream(file) ;
            outputStream.write(multipartFile.getBytes());
            PutObjectRequest request = new PutObjectRequest(s3bucket, filePath, file);
            amazonS3.putObject(request);
            outputStream.close();
            file.delete();
        } catch (final IOException ex) {
            System.out.println("Error converting the multi-part file to file= "+ex.getMessage());
        }
        return "https://" + s3bucket + ".s3.amazonaws.com/" + filePath;
    }

    public String getFilePath(MultipartFile multipartFile,String bucket_folder ,String str1, String str2){
        String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        String filename = str1 + "_" + str2 + "_" + System.currentTimeMillis();
        String path =  bucket_folder + filename + "." + extension;
        return path;
    };
}
