package com.github.rahmnathan.localmovie.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class CorrelationIdFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(CorrelationIdFilter.class);
    private static final String X_CORRELATION_ID = "x-correlation-id";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String correlationId = httpServletRequest.getHeader(X_CORRELATION_ID);

        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
            logger.info("No correlationId found in Header. Generated : {}", correlationId);
        } else {
            logger.info("Found correlationId in Header : {}", correlationId);
        }

        MDC.put(X_CORRELATION_ID, correlationId);

        filterChain.doFilter(httpServletRequest, servletResponse);

        MDC.clear();
    }
}