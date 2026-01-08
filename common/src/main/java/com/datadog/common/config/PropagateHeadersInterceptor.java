package com.datadog.common.config;

import com.datadog.common.constant.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class PropagateHeadersInterceptor implements RequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
            List<String> headers = Collections.list(request.getHeaderNames());
            updateHeaders(request, headers, template);
            template.removeHeader(Constants.CORRELATION_ID_HEADER);
            template.header(Constants.CORRELATION_ID_HEADER, MDC.get(Constants.CORRELATION_ID_MDC_KEY));
        }
    }

    private void updateHeaders(
            HttpServletRequest request,
            List<String> headers,
            RequestTemplate template
    ) {
        headers.forEach(header -> {
            if (header.startsWith("x-")) {
                log.trace("Propagating header: {} - {}", header, request.getHeader(header));
                String value = request.getHeader(header);
                template.header(header, value);
            }
        });
    }
}
