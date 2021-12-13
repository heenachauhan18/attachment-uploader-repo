package com.jira.test.demo;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

@Component
public class JiraService {
    private final FileS3Service fileS3Service;
    private final HttpClientProvider httpClientProvider = new HttpClientProvider();

    public JiraService(FileS3Service fileS3Service) {
        this.fileS3Service = fileS3Service;
    }

    public boolean createIssue(String token) {
        S3Object s3Object = fileS3Service.getObject("attachment-uploader-test-azure", "39531967-8d9a-430a-993f-30b1a2072f80.mp4");
        byte[] byteArray = new byte[0];
        try {
            byteArray = IOUtils.toByteArray(s3Object.getObjectContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = httpClientProvider.httpClient();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "filename",
                        RequestBody.create(MediaType.parse("application/octet-stream"), byteArray))
                .build();

        System.out.println("Data on hand, calling JIRA");
        Request request = new Request.Builder()
                .url("https://api.atlassian.com/ex/jira/31bde944-3a2c-4bf8-9738-6f7b505fdce4//rest/api/3/issue/WEB-102/attachments")
                .method("POST", body)
                .addHeader("Authorization", token)
                .addHeader("X-Atlassian-Token", "no-check")
                .build();

        try {
            System.out.println("final call");
            Response execute = okHttpClient.newCall(request).execute();
            System.out.println("completed call");
            System.out.println(execute.body().string());
            return true;
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return false;
    }
}
