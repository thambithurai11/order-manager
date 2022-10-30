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
 * OrderConfig
 * All configs which are relevant for application.
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

    @Bean(name = "apiRestTemplate")
    public RestTemplate apiRestTemplate() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return new RestTemplate(factory);
    }


    /**
     * Thread Executor pool
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
     * open-api configuration about application name and version
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        final String apiTitle = String.format("%s", StringUtils.capitalize(appName));
        return new OpenAPI()
                .info(new Info().title(apiTitle).version(appVersion).description(appDescription));
    }
}
