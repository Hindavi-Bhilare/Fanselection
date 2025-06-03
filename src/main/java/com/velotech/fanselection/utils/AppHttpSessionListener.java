
package com.velotech.fanselection.utils;

import java.io.File;


import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class AppHttpSessionListener implements HttpSessionListener {

	static Logger log = LogManager.getLogger(AppHttpSessionListener.class.getName());

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {

		httpSessionEvent.getSession().setMaxInactiveInterval(2 * 60 * 60);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

		try {
			String path = (String) httpSessionEvent.getSession().getAttribute("userRealPath");
			File f1 = new File(path);
			if (f1.exists()) {
				File[] files = f1.listFiles();
				for (Integer i = 0; i < files.length; i++) {
					if (files[i].isFile())
						files[i].delete();
				}
				f1.delete();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
