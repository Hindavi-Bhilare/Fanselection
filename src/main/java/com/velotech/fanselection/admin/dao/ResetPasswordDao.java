
package com.velotech.fanselection.admin.dao;

import java.util.List;

import com.velotech.fanselection.models.Tbl52Usermaster;

public interface ResetPasswordDao {

	List<Tbl52Usermaster> getUserMasters();

	boolean updateUserMaster(Tbl52Usermaster tbl52Usermaster);

}
