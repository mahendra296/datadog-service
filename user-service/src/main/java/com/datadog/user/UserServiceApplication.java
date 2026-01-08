package com.datadog.user;

import com.datadog.common.client.ProfileServiceClient;
import com.datadog.common.config.InterceptorConfig;
import com.datadog.common.config.LogbookConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
        exclude = {
            org.springframework.cloud.autoconfigure.RefreshAutoConfiguration.class,
            org.springframework.cloud.autoconfigure.LifecycleMvcEndpointAutoConfiguration.class
        })
@Import(value = {InterceptorConfig.class, LogbookConfig.class})
@EnableFeignClients(clients = {ProfileServiceClient.class})
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
