module local.movie.web.service {
    requires movie.info.omdb;
    requires spring.web;
    requires slf4j.api;
    requires localmovie.domain;
    requires org.apache.httpcomponents.httpcore;
    requires javax.servlet.api;
    requires micrometer.core;
    requires spring.beans;
    requires camel.core;
    requires spring.context;
    requires jedis;
    requires spring.boot;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.module.paramnames;
    requires spring.vault.core;
    requires spring.cloud.vault.config;
    requires jackson.annotations;
    requires spring.boot.autoconfigure;

}