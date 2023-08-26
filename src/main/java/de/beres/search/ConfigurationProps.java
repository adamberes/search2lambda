package de.beres.search;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

//@Data
@RequiredArgsConstructor
@Data
@ConfigurationProperties(prefix = "metadata")
@Configuration
public class ConfigurationProps {
    private String pdf;
    private String docx;
    private String jpg;
    private String nef;
    private Path targetDir;
}
