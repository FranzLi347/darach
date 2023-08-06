package io.github.franzli347.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtConfig {
    String header;
    String secret;
    String expiration;
}
