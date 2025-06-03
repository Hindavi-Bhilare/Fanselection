
package com.velotech.fanselection.user.dao;

import com.velotech.fanselection.models.Tbl00ClientMaster;
import com.velotech.fanselection.models.Tbl52Usermaster;

public interface UserDao {

	Tbl52Usermaster userLogin(String loginId, String passWord);

	Tbl52Usermaster getUserMaster(String loginId);

	boolean checkUserIsAdmin(String loginId);

	boolean checkUserIsMarketinHead(String loginId);

	Tbl00ClientMaster getClient();
}
