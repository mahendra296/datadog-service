package com.datadog.common.config;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "logbook")
public class LogbookProperties {

    private ObfuscateProperties obfuscate = new ObfuscateProperties();

    public ObfuscateProperties getObfuscate() {
        return obfuscate;
    }

    public void setObfuscate(ObfuscateProperties obfuscate) {
        this.obfuscate = obfuscate;
    }

    public static class ObfuscateProperties {
        private Set<String> headers = Set.of();
        private Set<String> parameters = Set.of();
        private Set<String> bodyFields = Set.of();
        private Map<String, List<String>> bodyFieldsJsonPath = Map.of();

        public Set<String> getHeaders() {
            return headers;
        }

        public void setHeaders(Set<String> headers) {
            this.headers = headers;
        }

        public Set<String> getParameters() {
            return parameters;
        }

        public void setParameters(Set<String> parameters) {
            this.parameters = parameters;
        }

        public Set<String> getBodyFields() {
            return bodyFields;
        }

        public void setBodyFields(Set<String> bodyFields) {
            this.bodyFields = bodyFields;
        }

        public Map<String, List<String>> getBodyFieldsJsonPath() {
            return bodyFieldsJsonPath;
        }

        public void setBodyFieldsJsonPath(Map<String, List<String>> bodyFieldsJsonPath) {
            this.bodyFieldsJsonPath = bodyFieldsJsonPath;
        }
    }
}
