package com.every.expence.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        String finalUrl = url;
        try {
            java.net.InetAddress.getByName("minio");
            // If we are in docker, 'minio' will resolve
            if (url.contains("localhost") || url.contains("127.0.0.1")) {
                finalUrl = url.replace("localhost", "minio").replace("127.0.0.1", "minio");
            }
        } catch (Exception e) {
            // Not in docker, keep the original url (e.g. localhost)
        }

        return MinioClient.builder()
                .endpoint(finalUrl)
                .credentials(accessKey, secretKey)
                .build();
    }
}
