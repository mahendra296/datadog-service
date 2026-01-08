package com.datadog.common.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

import static com.datadog.common.constant.Constants.*;
import static com.datadog.common.constant.Constants.CORRELATION_ID_MDC_KEY;
import static com.datadog.common.constant.Constants.DEFAULT_PLATFORM;
import static com.datadog.common.constant.Constants.HEADER_API_PLATFORM;
import static com.datadog.common.constant.Constants.HEADER_DATADOG_TRACE_ID;
import static com.datadog.common.constant.Constants.PLATFORM_MDC_KEY;

@Component
@Order(1)
@Slf4j
public class CorrelationIdFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            String correlationId = extractOrGenerateCorrelationId(httpRequest);
            String platform = extractPlatform(httpRequest);
            MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
            MDC.put(PLATFORM_MDC_KEY, platform);
            httpResponse.setHeader(CORRELATION_ID_HEADER, correlationId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(CORRELATION_ID_MDC_KEY);
            MDC.remove(PLATFORM_MDC_KEY);
        }
    }

    private String extractOrGenerateCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(HEADER_DATADOG_TRACE_ID);
        if (StringUtils.hasText(correlationId)) {
            return correlationId;
        }
        return UUID.randomUUID().toString();
    }

    private String extractPlatform(HttpServletRequest request) {
        String platform = request.getHeader(HEADER_API_PLATFORM);
        if (StringUtils.hasText(platform)) {
            return platform.toUpperCase();
        }
        return DEFAULT_PLATFORM;
    }
}
