package com.exam.ExamClient.configuration;

import com.exam.ExamClient.rest.RestDiary;
import com.exam.ExamClient.rest.RestUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {
    @Bean
    public RestUser restUser(
            @Value("${basicURL}") String basicUrl
    ) {
        return new RestUser(
                RestClient.builder()
                        .baseUrl(basicUrl)
//                        .requestInterceptor(
//                                new BasicAuthenticationInterceptor(
//                                        username,
//                                        password
//                                )
//                        )
                        .build()

        );
    }

    @Bean
    public RestDiary restDiary(
            @Value("${basicURL}") String basicUrl
    ) {
        return new RestDiary(
                RestClient.builder()
                        .baseUrl(basicUrl)
//                        .requestInterceptor(
//                                new BasicAuthenticationInterceptor(
//                                        username,
//                                        password
//                                )
//                        )
                        .build()

        );
    }
}
