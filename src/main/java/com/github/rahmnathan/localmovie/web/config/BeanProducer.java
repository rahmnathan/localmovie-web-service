package com.github.rahmnathan.localmovie.web.config;

import com.github.rahmnathan.localmovie.web.filter.CorrelationIdFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class BeanProducer {
    private final String jedisHost;

    public BeanProducer(@Value("${jedis.host}") String jedisHost) {
        this.jedisHost = jedisHost;
    }

    @Bean
    public JedisPool buildJedisPool(){
        return new JedisPool(new JedisPoolConfig(), jedisHost);
    }

    @Bean
    public FilterRegistrationBean<CorrelationIdFilter> loggingFilter(){
        FilterRegistrationBean<CorrelationIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorrelationIdFilter());
        registrationBean.addUrlPatterns("/localmovies/*");
        return registrationBean;
    }
}
