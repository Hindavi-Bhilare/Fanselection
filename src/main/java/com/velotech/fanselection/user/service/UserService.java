
package com.velotech.fanselection.user.service;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.velotech.fanselection.utils.AppException;

public interface UserService {

	JSONObject userLogin(HttpServletRequest request, String loginId, String passWord) throws AppException;

	JSONObject getUserSessionData(HttpServletRequest request) throws AppException;

	JSONObject getUserData(HttpServletRequest request) throws AppException;

}
