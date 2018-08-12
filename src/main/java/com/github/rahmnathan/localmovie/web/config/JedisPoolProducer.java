package com.github.rahmnathan.localmovie.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisPoolProducer {
    private final String jedisHost;

    public JedisPoolProducer(@Value("${jedis.host}") String jedisHost) {
        this.jedisHost = jedisHost;
    }

    @Bean
    public JedisPool buildJedisPool(){
        return new JedisPool(new JedisPoolConfig(), jedisHost);
    }
}
