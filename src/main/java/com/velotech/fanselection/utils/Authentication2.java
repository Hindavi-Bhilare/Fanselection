
package com.velotech.fanselection.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

@WebFilter("/*")
public class Authentication2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        
        System.out.println(httpRequest.getRequestURL());
        String uri = httpRequest.getRequestURI();
        System.out.println(uri);
        String applicationName = determineApplicationName(uri);
        System.setProperty("com.newrelic.agent.APPLICATION_NAME", applicationName);
        servletRequest.setAttribute("com.newrelic.agent.APPLICATION_NAME", applicationName);

        // Continue the filter chain
        filterChain.doFilter(servletRequest, servletResponse);
        
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }

    private String determineApplicationName(String uri) {
        // Logic to determine application name based on URI
        // You can implement any custom logic here
        // For example:
        if (uri.startsWith("/pumpselection_submersible_shakti")) {
            return "pumpselection_submersible_shakti";
        } /*else if (uri.startsWith("/app2")) {
            return "MySpecialWebApp2";
        } */else {
            return "DefaultWebApp";
        }
    }
}