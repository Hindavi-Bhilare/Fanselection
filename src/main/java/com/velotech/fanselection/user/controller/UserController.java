
package com.velotech.fanselection.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.user.service.UserService;
import com.velotech.fanselection.utils.AppException;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.SessionCountListener;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	static Logger log = LogManager.getLogger(UserController.class.getName());

	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionCountListener sessionCounter;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, @RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) throws AppException {

		JSONObject finalJSON = new JSONObject();
		try {
			
			finalJSON = userService.userLogin(request, username, password);
			// Debugging
			System.out.println("Username received from request: " + username);
			MDC.put("username", username);
			System.out.println("Username set in logging context: " + MDC.get("username"));

		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
			throw new AppException(ApplicationConstants.NULL_POINTER_EXCEPTION__MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new AppException(ApplicationConstants.CONTROLLER_EXCEPTION_MSG);
		}/*finally {
            // Clear user details from logging context
            MDC.remove("username");
        }*/
		return finalJSON.toString();
	}

	@RequestMapping(value = "/getuserdata", method = RequestMethod.GET)
	public String getUserData(HttpServletRequest request) throws AppException {

		JSONObject finalJSON = new JSONObject();
		try {
			finalJSON = userService.getUserData(request);
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
			throw new AppException(ApplicationConstants.NULL_POINTER_EXCEPTION__MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new AppException(ApplicationConstants.CONTROLLER_EXCEPTION_MSG);
		}
		return finalJSON.toString();
	}

	@RequestMapping(value = "/getUserSessionData", method = RequestMethod.GET)
	public String getUserSessionData(HttpServletRequest request) throws AppException {

		JSONObject finalJSON = new JSONObject();
		try {
			finalJSON = userService.getUserSessionData(request);
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
			throw new AppException(ApplicationConstants.NULL_POINTER_EXCEPTION__MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new AppException(ApplicationConstants.CONTROLLER_EXCEPTION_MSG);
		}
		return finalJSON.toString();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) throws AppException {

		try {
			httpSession.invalidate();
			 MDC.remove("username");
			System.out.println("Active Sessions are : " + sessionCounter.getActiveSessions());
		} catch (NullPointerException e) {
			log.error(e.getMessage(), e);
			throw new AppException(ApplicationConstants.NULL_POINTER_EXCEPTION__MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new AppException(ApplicationConstants.CONTROLLER_EXCEPTION_MSG);
		}
		return "true";
	}
}
