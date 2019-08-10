module localmovie.web.service {
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires slf4j.api;
    requires javaee.api;
    requires micrometer.core;
    requires localmovie.web.domain;
    requires spring.boot;
    requires com.fasterxml.jackson.core;
    requires jackson.annotations;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires kotlin.stdlib;
    requires org.apache.httpcomponents.httpcore;
    requires spring.web;
    requires spring.data.jpa;
}