
package com.velotech.fanselection.user.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl00ClientMaster;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl53Userrole;
import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.ResourceUtil;
import com.velotech.fanselection.utils.StringEncryptor;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class UserDaoImpl extends HibernateSession implements UserDao {

	static Logger log = LogManager.getLogger(UserDaoImpl.class.getName());

	@Autowired
	VelotechUtil velotechUtil;

	@Override
	public Tbl52Usermaster userLogin(String loginId, String passWord) {

		Tbl52Usermaster tbl52Usermaster = null;
		try {
			StringEncryptor stringEncrypter = new StringEncryptor(new ResourceUtil().getPropertyValues("encryptionScheme"),
					new ResourceUtil().getPropertyValues("encryptionKey"));
			String encryptedPassword = stringEncrypter.encrypt(passWord);

			Criteria criteria = getSession().createCriteria(Tbl52Usermaster.class);
			criteria.add(Restrictions.eq("loginId", loginId));
			criteria.add(Restrictions.eq("password", encryptedPassword));
			criteria.add(Restrictions.eq("isActive", true));

			tbl52Usermaster = (Tbl52Usermaster) criteria.uniqueResult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl52Usermaster;
	}

	@Override
	public Tbl52Usermaster getUserMaster(String loginId) {

		Tbl52Usermaster tbl52Usermaster = null;
		try {
			tbl52Usermaster = getSession().get(Tbl52Usermaster.class, loginId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl52Usermaster;
	}

	@Override
	public boolean checkUserIsAdmin(String loginId) {

		boolean isAdmin = false;
		try {
			Criteria criteria = getSession().createCriteria(Tbl53Userrole.class);
			criteria.createAlias("tbl56Department", "tbl56Department");
			criteria.add(Restrictions.eq("tbl52Usermaster.loginId", loginId));
			criteria.add(Restrictions.eq("tbl56Department.department", "Admin"));

			List<Tbl53Userrole> models = criteria.list();
			isAdmin = models.size() > 0 ? true : false;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isAdmin;
	}
	
	public boolean checkUserIsMarketinHead(String loginId) {

		boolean isMarketinHead = false;
		try {
			Criteria criteria = getSession().createCriteria(Tbl53Userrole.class);
			criteria.createAlias("tbl56Department", "tbl56Department");
			criteria.createAlias("tbl54Rolemaster", "tbl54Rolemaster");
			criteria.add(Restrictions.eq("tbl52Usermaster.loginId", loginId));
			criteria.add(Restrictions.eq("tbl56Department.department", "Marketing"));
			criteria.add(Restrictions.eq("tbl54Rolemaster.role", "Head"));

			List<Tbl53Userrole> models = criteria.list();
			isMarketinHead = models.size() > 0 ? true : false;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isMarketinHead;
	}

	@Override
	public Tbl00ClientMaster getClient() {

		Tbl00ClientMaster tbl00ClientMaster = null;
		try {

			Criteria criteria = getSession().createCriteria(Tbl00ClientMaster.class);
			criteria.add(Restrictions.eq("clientId", velotechUtil.getCompany()));

			tbl00ClientMaster = (Tbl00ClientMaster) criteria.list().get(0);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tbl00ClientMaster;
	}

}
