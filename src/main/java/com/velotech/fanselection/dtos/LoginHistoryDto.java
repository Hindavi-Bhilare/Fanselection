
package com.velotech.fanselection.dtos;

public class LoginHistoryDto {

	private int id;

	private String userMasterloginId;

	private String loginDate;

	private String ipAddress;

	private int count;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getUserMasterloginId() {

		return userMasterloginId;
	}

	public void setUserMasterloginId(String userMasterloginId) {

		this.userMasterloginId = userMasterloginId;
	}

	public String getLoginDate() {

		return loginDate;
	}

	public void setLoginDate(String loginDate) {

		this.loginDate = loginDate;
	}

	public String getIpAddress() {

		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {

		this.ipAddress = ipAddress;
	}

	public int getCount() {

		return count;
	}

	public void setCount(int count) {

		this.count = count;
	}

}
