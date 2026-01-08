package com.datadog.common.config;

import jakarta.servlet.Filter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.HeaderFilter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.QueryFilter;
import org.zalando.logbook.core.BodyFilters;
import org.zalando.logbook.core.Conditions;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.core.HeaderFilters;
import org.zalando.logbook.core.QueryFilters;
import org.zalando.logbook.json.JsonBodyFilters;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.json.JsonPathBodyFilters;
import org.zalando.logbook.okhttp.GzipInterceptor;
import org.zalando.logbook.okhttp.LogbookInterceptor;
import org.zalando.logbook.servlet.LogbookFilter;

@Configuration
@EnableConfigurationProperties(LogbookProperties.class)
public class LogbookConfig {

    private final LogbookProperties properties;

    public LogbookConfig(LogbookProperties properties) {
        this.properties = properties;
    }

    @Bean
    public BodyFilter bodyFilter() {
        Set<String> bodyFields = properties.getObfuscate().getBodyFields();
        Map<String, List<String>> bodyFieldsJsonPath = properties.getObfuscate().getBodyFieldsJsonPath();

        BodyFilter bodyFilter = BodyFilter.merge(
                BodyFilters.defaultValue(), JsonBodyFilters.replaceJsonStringProperty(bodyFields, "XXX"));

        if (bodyFieldsJsonPath != null && !bodyFieldsJsonPath.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : bodyFieldsJsonPath.entrySet()) {
                String patternString = entry.getValue().stream().collect(Collectors.joining("|"));
                Pattern pattern = Pattern.compile(patternString, Pattern.MULTILINE);
                bodyFilter = BodyFilter.merge(
                        bodyFilter,
                        JsonPathBodyFilters.jsonPath("$." + entry.getKey()).replace(pattern, "XXX"));
            }
        }

        return bodyFilter;
    }

    @Bean
    public HeaderFilter headerFilter() {
        Set<String> headers = properties.getObfuscate().getHeaders();
        if (headers == null || headers.isEmpty()) {
            return HeaderFilters.defaultValue();
        }
        return HeaderFilter.merge(HeaderFilters.defaultValue(), HeaderFilters.replaceHeaders(headers, "XXX"));
    }

    @Bean
    public QueryFilter queryFilter() {
        Set<String> parameters = properties.getObfuscate().getParameters();
        if (parameters == null || parameters.isEmpty()) {
            return QueryFilters.defaultValue();
        }
        return QueryFilter.merge(QueryFilters.defaultValue(), QueryFilters.replaceQuery(parameters::contains, "XXX"));
    }

    @Bean
    public Logbook logbook(BodyFilter bodyFilter, HeaderFilter headerFilter, QueryFilter queryFilter) {
        return Logbook.builder()
                .condition(Conditions.exclude(
                        Conditions.requestTo("/management/**"), Conditions.requestTo("/actuator/**")))
                .queryFilter(queryFilter)
                .headerFilter(headerFilter)
                .bodyFilter(bodyFilter)
                .sink(new DefaultSink(new JsonHttpLogFormatter(), new DefaultHttpLogWriter()))
                .build();
    }

    @Bean
    public FilterRegistrationBean<Filter> logbookFilter(Logbook logbook) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LogbookFilter(logbook));
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        registration.addUrlPatterns("/*");
        registration.setName("logbookFilter");
        return registration;
    }

    @Bean
    public OkHttpClient.Builder okHttpClientBuilder(Logbook logbook) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new LogbookInterceptor(logbook))
                .addNetworkInterceptor(new GzipInterceptor());
    }
}
