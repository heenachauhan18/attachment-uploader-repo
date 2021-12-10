package com.jira.test.demo;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JiraService {
    private final FileS3Service fileS3Service;
    private final HttpClientProvider httpClientProvider = new HttpClientProvider();

    public JiraService(FileS3Service fileS3Service) {
        this.fileS3Service = fileS3Service;
    }

    public boolean createIssue() {
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
                .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik16bERNemsxTVRoRlFVRTJRa0ZGT0VGRk9URkJOREJDTVRRek5EZzJSRVpDT1VKRFJrVXdNZyJ9.eyJodHRwczovL2F0bGFzc2lhbi5jb20vb2F1dGhDbGllbnRJZCI6IlNRQ2RiUGJxSng2TmRHUnNjTWdaWmtDdEVQbzNJTWJlIiwiaHR0cHM6Ly9hdGxhc3NpYW4uY29tL2VtYWlsRG9tYWluIjoiYWptZXJhaW5mb3RlY2guY29tIiwiaHR0cHM6Ly9hdGxhc3NpYW4uY29tL3N5c3RlbUFjY291bnRJZCI6IjYwYjVlMDA2NDZlMjc4MDA2ODdmZDgzYSIsImh0dHBzOi8vYXRsYXNzaWFuLmNvbS9zeXN0ZW1BY2NvdW50RW1haWxEb21haW4iOiJjb25uZWN0LmF0bGFzc2lhbi5jb20iLCJodHRwczovL2F0bGFzc2lhbi5jb20vdmVyaWZpZWQiOnRydWUsImh0dHBzOi8vYXRsYXNzaWFuLmNvbS9maXJzdFBhcnR5IjpmYWxzZSwiaHR0cHM6Ly9hdGxhc3NpYW4uY29tLzNsbyI6dHJ1ZSwiaXNzIjoiaHR0cHM6Ly9hdGxhc3NpYW4tYWNjb3VudC1wcm9kLnB1czIuYXV0aDAuY29tLyIsInN1YiI6ImF1dGgwfDU1NzA1ODoxOGU4MzljZS04MWEzLTQ1YWItYmZjYy1jNzQxZWM1ZGJmZTEiLCJhdWQiOiJhcGkuYXRsYXNzaWFuLmNvbSIsImlhdCI6MTYzODg1MzUwNywiZXhwIjoxNjM4ODU3MTA3LCJhenAiOiJTUUNkYlBicUp4Nk5kR1JzY01nWlprQ3RFUG8zSU1iZSIsInNjb3BlIjoid3JpdGU6amlyYS13b3JrIHJlYWQ6amlyYS13b3JrIHJlYWQ6amlyYS11c2VyIG9mZmxpbmVfYWNjZXNzIn0.MHb0536Kcyf6TINj94WoHJr1jFQEFnukm9zTo_Me6CzAodf9Cgj0BKEgfBomVw0UxMjRAmDkRwYtiFdreOKlmXR-EyRjtHcLIWQ7Z-6khQWWll_3TodzZ0AUjsHIvyJ4u0vZ6VbVcS2GpayQ5Uwm7-RJFxneOkfUzdb_U7oiPylNdkKhbIUM8zmU3MPatjPdjBRXpNUq83-qG-f9F8UQSHPsKbzWRmELyF812kNCODJWOOv8XtlwPlEm-wlHLFKpd25Zfh0v6uBOP81BYC5FIxIAg3EfuJArA0MocTcFQa0A4LEXL9pIVyNCxOjTt87y9JHzBWRzSXC5I_igaoe22A")
                .addHeader("X-Atlassian-Token", "no-check")
                .build();

        try {
            System.out.println("final call");
            Response execute = okHttpClient.newCall(request).execute();
            System.out.println("completed call");
            System.out.println(execute.body().string());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
