package com.datadog.profile;

import com.datadog.common.config.CorrelationIdFilter;
import com.datadog.common.config.LogbookConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {CorrelationIdFilter.class, LogbookConfig.class})
public class ProfileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProfileServiceApplication.class, args);
    }
}
