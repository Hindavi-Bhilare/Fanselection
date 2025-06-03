package com.velotech.fanselection.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private  SessionCountListener sessionCounterListener;

    public int getActiveSessionCount() {
        return sessionCounterListener.getActiveSessions();
    }
}

