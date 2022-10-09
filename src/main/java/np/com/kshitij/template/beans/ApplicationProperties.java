package np.com.kshitij.template.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("application")
public class ApplicationProperties {
    private String title = "";
    private String version = "";
    private String description = "";
    private String termsOfServiceUrl = "";
    private String license = "";
    private String licenseUrl = "";
    private Developer developer = new Developer();

    @Data
    public static class Developer {
        private String name = "";
        private String url = "";
        private String email = "";
    }
}
