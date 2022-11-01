package com.vodafoneziggo.ordermanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

/**
 * This is Configuration class that contains the below Bean configurations for the Application
 * 1. apiRestTemplate - RestTemplate for Users API
 * 2. taskExecutor - TaskExecutor for Async calls
 * 3. orderManagerOpenAPI - OpenAPI for Swagger
 *
 * @author Thambi Thurai Chinnadurai
 */
@Configuration
@EnableAsync
public class OrderConfig {

    @Value("${order.api.connectTimeout}")
    private Integer connectTimeout;

    @Value("${order.api.readTimeout}")
    private Integer readTimeout;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.version}")
    private String appVersion;

    @Value("${spring.application.description}")
    private String appDescription;

    /**
     * RestTemplate Configuration for Users API
     *
     * @return apiRestTemplate
     */
    @Bean(name = "apiRestTemplate")
    public RestTemplate apiRestTemplate() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return new RestTemplate(factory);
    }


    /**
     * Thread Task Executor pool
     *
     * @return Executor
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("OrderThread");
        executor.initialize();
        return executor;
    }

    /**
     * This Open API Configuration for Swagger
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI orderManagerOpenAPI() {
        final String apiTitle = String.format("%s", StringUtils.capitalize(appName));
        return new OpenAPI()
                .info(new Info().title(apiTitle).version(appVersion).description(appDescription));
    }
}
