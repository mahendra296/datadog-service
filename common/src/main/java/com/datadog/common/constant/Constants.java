package com.datadog.common.constant;

public final class Constants {

    private Constants() {}

    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    public static final String HEADER_DATADOG_TRACE_ID = "x-datadog-trace-id";
    public static final String CORRELATION_ID_MDC_KEY = "correlationId";
    public static final String HEADER_API_PLATFORM = "x-user-platform";
    public static final String PLATFORM_MDC_KEY = "platform";
    public static final String DEFAULT_PLATFORM = "POSTMAN";
}
