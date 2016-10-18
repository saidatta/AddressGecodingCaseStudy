package com.adbobe.app.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.ServletContext;

/**
 * Created by venkatamunnangi on 10/12/16.
 */
@Order(10)
public class WebApplicationInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        DelegatingFilterProxy filter = new DelegatingFilterProxy("oauth2ClientContextFilter");
        filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
        container.addFilter("oauth2ClientContextFilter", filter).addMappingForUrlPatterns(null, false, "/*");
    }
}
