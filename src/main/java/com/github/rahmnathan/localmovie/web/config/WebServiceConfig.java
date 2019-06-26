package com.github.rahmnathan.localmovie.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service")
public class WebServiceConfig {
    private String[] mediaPaths;
    private String jedisHost;

    public String[] getMediaPaths() {
        return mediaPaths;
    }

    public void setMediaPaths(String[] mediaPaths) {
        this.mediaPaths = mediaPaths;
    }

    public String getJedisHost() {
        return jedisHost;
    }

    public void setJedisHost(String jedisHost) {
        this.jedisHost = jedisHost;
    }
}
