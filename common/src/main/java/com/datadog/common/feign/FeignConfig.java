package com.datadog.common.feign;

import feign.Logger;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor correlationIdRequestInterceptor() {
        return requestTemplate -> {
            String correlationId = MDC.get(CORRELATION_ID_MDC_KEY);
            if (correlationId != null) {
                requestTemplate.header(CORRELATION_ID_HEADER, correlationId);
            }
        };
    }
}
