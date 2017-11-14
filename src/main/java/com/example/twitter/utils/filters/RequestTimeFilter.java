package com.example.twitter.utils.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter("/*")
public class RequestTimeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTimeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            time = System.currentTimeMillis() - time;
            logger.info("request: " + ((HttpServletRequest) request).getRequestURI() + ", time: " + time + " ms");
        }
    }

    @Override
    public void destroy() {

    }
}
