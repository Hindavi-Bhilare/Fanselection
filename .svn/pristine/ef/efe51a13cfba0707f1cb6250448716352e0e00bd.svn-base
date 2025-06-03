
package com.velotech.fanselection.user.service;

import java.io.File;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl00ClientMaster;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl53Userrole;
import com.velotech.fanselection.models.Tbl57LoginHistory;
import com.velotech.fanselection.user.dao.UserDao;
import com.velotech.fanselection.utils.AppException;
import com.velotech.fanselection.utils.SessionCountListener;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
@WebListener
public class UserServiceImpl implements UserService, HttpSessionListener {

	static Logger log = LogManager.getLogger(UserServiceImpl.class.getName());

	@Autowired
	private UserDao userDao;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;
	
	
	@Autowired
	private SessionCountListener sessionCounter;

	@Autowired
	private ServletContext servletContext;

	@Override
	public JSONObject userLogin(final HttpServletRequest request, final String loginId, final String passWord) throws AppException {

		JSONObject finalJSON = new JSONObject();
		Tbl52Usermaster tbl52Usermaster = new Tbl52Usermaster();
		try {
			tbl52Usermaster = userDao.userLogin(loginId, passWord);
			if (tbl52Usermaster != null) {
				finalJSON.put("data", "");
				finalJSON.put("message", "do Login");
				finalJSON.put("success", true);

				setUserSessionData(request.getSession(true), tbl52Usermaster);
			} else {
				finalJSON.put("data", "");
				finalJSON.put("message", "Wrong Combination of username and password");
				finalJSON.put("success", false);
			}
			
			System.out.println("Active Sessions are : " + sessionCounter.getActiveSessions());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return finalJSON;
	}

	private void setUserSessionData(HttpSession httpSession, Tbl52Usermaster tbl52Usermaster) throws AppException {

		try {

			httpSession.setAttribute("sessionId", httpSession.getId());
			httpSession.setAttribute("loginId", tbl52Usermaster.getLoginId());
			httpSession.setAttribute("username", tbl52Usermaster.getUserName());
			httpSession.setAttribute("company", tbl52Usermaster.getCompany());
			httpSession.setAttribute("usertypeId", tbl52Usermaster.getTbl50Usertype().getId());
						
			boolean isAdminUser = userDao.checkUserIsAdmin(tbl52Usermaster.getLoginId());
			httpSession.setAttribute("isAdminUser", isAdminUser);

			boolean isMarketingHead = userDao.checkUserIsMarketinHead(tbl52Usermaster.getLoginId());
			httpSession.setAttribute("isMarketingHead", isMarketingHead);

			@SuppressWarnings("unchecked")
			List<Tbl28CompanyMaster> tbl28CompanyMasters = (List<Tbl28CompanyMaster>) genericDao.findByParam(Tbl28CompanyMaster.class, "company",
					tbl52Usermaster.getCompany());

			httpSession.setAttribute("companyId", tbl28CompanyMasters.get(0).getId());
			String path = servletContext.getRealPath(System.getProperty("file.separator") + "userTemp" + System.getProperty("file.separator"));

			String userRealPath = servletContext.getRealPath(System.getProperty("file.separator") + "userTemp" + System.getProperty("file.separator")
					+ httpSession.getId() + "" + tbl52Usermaster.getUserName());

			String userContextPath = servletContext.getContextPath() + System.getProperty("file.separator") + "userTemp"
					+ System.getProperty("file.separator") + httpSession.getId() + "" + tbl52Usermaster.getUserName();

			String companyContextPath = servletContext.getContextPath() + System.getProperty("file.separator") + "company"
					+ System.getProperty("file.separator") + tbl52Usermaster.getCompany();

			
			String projectRealPath = servletContext.getRealPath("");

			File f0 = new File(path);
			if (!f0.exists()) {
				f0.mkdir();
			}

			File f = new File(userRealPath);
			if (!f.exists()) {
				f.mkdir();
			}

			httpSession.setAttribute("userContextPath", userContextPath);
			httpSession.setAttribute("userRealPath", userRealPath);
			System.out.println("userRealPath:" + userRealPath);
			httpSession.setAttribute("projectRealPath", projectRealPath);
			httpSession.setAttribute("companyContextPath", companyContextPath);

			setUserLoginHistory(tbl52Usermaster.getLoginId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void setUserLoginHistory(String loginid) {

		try {
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			Tbl57LoginHistory loginHistory = new Tbl57LoginHistory();
			Tbl52Usermaster userObj = new Tbl52Usermaster();
			userObj.setLoginId(loginid);

			String ip = requestAttributes.getRequest().getRemoteAddr();
			loginHistory.setIpAddress(ip);
			loginHistory.setTbl52Usermaster(userObj);
			loginHistory.setLoginDate(new Date());
			loginHistory.setCompany(velotechUtil.getCompany());
			genericDao.save(loginHistory);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public JSONObject getUserSessionData(final HttpServletRequest request) throws AppException {

		JSONObject finalJSON = new JSONObject();
		JSONObject dataJSON = new JSONObject();
		JSONArray userRoles = new JSONArray();
		try {
			HttpSession httpSession = request.getSession();
			Tbl52Usermaster tbl52Usermaster = userDao.getUserMaster(httpSession.getAttribute("loginId").toString());

			dataJSON.put("companyName", httpSession.getAttribute("companyName"));
			dataJSON.put("plantName", httpSession.getAttribute("companyPlantName"));
			dataJSON.put("unitName", httpSession.getAttribute("companyPlantUnitName"));
			dataJSON.put("yearCode", httpSession.getAttribute("yearCode"));
			dataJSON.put("startDate", httpSession.getAttribute("startDate"));
			dataJSON.put("endDate", httpSession.getAttribute("endDate"));
			dataJSON.put("username", tbl52Usermaster.getUserName());
			dataJSON.put("userroles", userRoles);
			finalJSON.put("data", dataJSON);
			finalJSON.put("success", true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return finalJSON;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getUserData(final HttpServletRequest request) throws AppException {

		JSONObject finalJSON = new JSONObject();

		try {
			HttpSession httpSession = request.getSession();
			Tbl52Usermaster tbl52Usermaster = userDao.getUserMaster(httpSession.getAttribute("loginId").toString());
			Tbl00ClientMaster tbl00ClientMaster = userDao.getClient();
			JSONObject dataJSON = new JSONObject();
			JSONArray userRoles = new JSONArray();
			boolean isAdmin = false;
			boolean isDesign = false;
			boolean isAdvance = false;
			boolean isTss = false;
			boolean isEffModifier = false;
			
			List<Tbl53Userrole> tbl53Userroles = (List<Tbl53Userrole>) genericDao.getRecordsByParentId(Tbl53Userrole.class, "tbl52Usermaster.loginId",
					tbl52Usermaster.getLoginId());
			for (Tbl53Userrole tbl53Userrole : tbl53Userroles) {
				JSONObject role = new JSONObject();
				role.put("department", tbl53Userrole.getTbl56Department().getDepartment());
				role.put("role", tbl53Userrole.getTbl54Rolemaster().getRole());
				userRoles.put(role);

				if (tbl53Userrole.getTbl56Department().getDepartment().equalsIgnoreCase("Admin"))
					isAdmin = true;

				if (tbl53Userrole.getTbl56Department().getDepartment().equalsIgnoreCase("Design"))
					isDesign = true;
				if (tbl53Userrole.getTbl56Department().getDepartment().equalsIgnoreCase("Marketing")
						&& tbl53Userrole.getTbl54Rolemaster().getRole().equalsIgnoreCase("Advance"))
					isAdvance = true;
				if (tbl53Userrole.getTbl56Department().getDepartment().equalsIgnoreCase("TSS"))
					isTss = true;
				if (tbl53Userrole.getTbl54Rolemaster().getRole().equalsIgnoreCase("Admin")
						|| tbl53Userrole.getTbl54Rolemaster().getRole().equalsIgnoreCase("Head")
						|| tbl53Userrole.getTbl54Rolemaster().getRole().equalsIgnoreCase("Advance"))
					isEffModifier = true;

			}

			dataJSON.put("username", tbl52Usermaster.getUserName());
			dataJSON.put("company", tbl00ClientMaster.getName());
			dataJSON.put("isAdmin", isAdmin);
			dataJSON.put("isDesign", isDesign);
			dataJSON.put("isAdvance", isAdvance);
			dataJSON.put("userroles", userRoles);
			dataJSON.put("isTss", isTss);
			dataJSON.put("isEffModifier", isEffModifier);

			finalJSON.put("data", dataJSON);
			finalJSON.put("success", true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return finalJSON;
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {

		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

		try {
			if (httpSessionEvent.getSession().getAttribute("loginId") != null) {
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
				System.out.println("sessionDestroyed User: " + httpSessionEvent.getSession().getAttribute("loginId"));
			}
		} catch (Exception ex) {
			System.out
					.println("sessionDestroyed User: " + httpSessionEvent.getSession().getAttribute("loginId") + " Can't able to delete all files.");
		}

	}
}
