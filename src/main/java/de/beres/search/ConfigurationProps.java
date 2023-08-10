package de.beres.search;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Data
@RequiredArgsConstructor
@Data
//@ConstructorBinding
@Component
@ConfigurationProperties(prefix = "metadata")
public class ConfigurationProps {
    private String pdf;
    private String docx;
    private String jpg;
    private String nef;
}
