package com.datadog.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public CorrelationIdFilter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

    @Bean
    public PropagateHeadersInterceptor propagateHeadersInterceptor() {
        return new PropagateHeadersInterceptor();
    }
}
