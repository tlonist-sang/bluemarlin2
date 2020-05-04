package com.project.bluemarlin2.bluemarlin2.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.properties")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationProperties {

    String secretKey;
}
