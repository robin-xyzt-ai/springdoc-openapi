package org.springdoc.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "springdoc")
public class SpringDocConfigProperties {

    private Boolean showActuator = false;
    private Webjars webjars = new Webjars();
    private ApiDocs apiDocs = new ApiDocs();

    public static class Webjars {
        private String prefix = "/webjars";

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }

    public static class ApiDocs {
        /**
         * Path to the generated OpenAPI documentation. For a yaml file, append ".yaml" to the path.
         */
        private String path = "/v3/api-docs";
        /**
         * Weather to generate and serve a OpenAPI document.
         */
        private Boolean enabled = true;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
    }

    public Boolean getShowActuator() {
        return showActuator;
    }

    public void setShowActuator(Boolean showActuator) {
        this.showActuator = showActuator;
    }

    public Webjars getWebjars() {
        return webjars;
    }

    public void setWebjars(Webjars webjars) {
        this.webjars = webjars;
    }

    public ApiDocs getApiDocs() {
        return apiDocs;
    }

    public void setApiDocs(ApiDocs apiDocs) {
        this.apiDocs = apiDocs;
    }
}