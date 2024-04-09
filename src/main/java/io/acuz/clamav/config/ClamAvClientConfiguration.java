package io.acuz.clamav.config;

import io.acuz.clamav.client.ClamAvClient;
import io.acuz.clamav.client.ClamAvClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class ClamAvClientConfiguration {
    private final ClamAvServerConfig config;

    public ClamAvClientConfiguration(ClamAvServerConfig config) {
        this.config = config;
    }

    @Bean
    public ClamAvClient clamAvClient() {
        return new ClamAvClientImpl(new InetSocketAddress(config.getHost(), config.getPort()));
    }
}
