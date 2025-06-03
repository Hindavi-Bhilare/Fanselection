
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

@WebFilter(urlPatterns = { "/report/*", "/file/*", "/dashboard/*", "/combogrid/*", "/combobox/*", "/familychart/*", "/selection/*", "/admin/*",
		"/design/*", "/others/*", "/offermaster/*", "/offer/*", "/spareoffermaster/*", "/spareofferline/*", "/spareoffertermsandconditions/*",
		"/offerprice/*", "/offerpumpprice/*", "/downloadoffer/*", "/mis/*", "/*/logout/", "/*/getuserdata", "/*/setUserSessionData",
		"/getUserSessionData/*" })
public class Authentication implements Filter {

	@Override
	public void destroy() {

		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		boolean success = false;
		HttpSession session = httpRequest.getSession(false);
		
//		session.getAttribute(name);
//		----------------new relic auto application name-------------
		 String requestUri = httpRequest.getRequestURI();
		

		   if (requestUri.startsWith("/pumpselection_submersible_shakti/")) {
		       request.setAttribute("com.newrelic.agent.APPLICATION_NAME", "MySpecialWebApp");
		    }
//			----------------new relic auto application name-------------
		   
		if (session == null || !httpRequest.isRequestedSessionIdValid()) {
			sendResponse(httpResponse, "NO ACTIVE SESSION.PLEASE RE-LOGIN.", success);
		} else {
			chain.doFilter(request, response);
		}
		return;
	}

	private void sendResponse(HttpServletResponse httpResponse, String msg, boolean success) {

		PrintWriter out;
		JSONObject result = new JSONObject();
		try {
			out = httpResponse.getWriter();
			httpResponse.setContentType("application/json");
			httpResponse.setHeader("Cache-Control", "no-store");
			result.put("message", "AUTHENTICATION FAILED");
			result.put("data", msg);
			result.put("success", success);
			out.print(result);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

		// TODO Auto-generated method stub

	}

}
