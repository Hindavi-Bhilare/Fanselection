
package com.velotech.fanselection.admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class ResetPasswordDaoImpl extends HibernateSession implements ResetPasswordDao {

	private static Logger log = LogManager.getLogger(ResetPasswordDaoImpl.class.getName());

	@Override
	public List<Tbl52Usermaster> getUserMasters() {

		List<Tbl52Usermaster> userMasters = new ArrayList<>();
		try {
			userMasters = getSession().createCriteria(Tbl52Usermaster.class).list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return userMasters;
	}

	@Override
	public boolean updateUserMaster(Tbl52Usermaster tbl52Usermaster) {

		boolean isUpdate = false;
		try {
			getSession().update(tbl52Usermaster);
			isUpdate = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isUpdate;
	}
}
