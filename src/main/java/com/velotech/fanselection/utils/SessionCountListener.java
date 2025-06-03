package com.velotech.fanselection.utils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;

@Component
public class SessionCountListener implements HttpSessionListener {

    private static int activeSessions = 0;

    public int getActiveSessions() {
        return activeSessions;
    }

    @Override
    public synchronized void sessionCreated(HttpSessionEvent se) {
        activeSessions++;
    }

    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent se) {
        activeSessions--;
    }
}

