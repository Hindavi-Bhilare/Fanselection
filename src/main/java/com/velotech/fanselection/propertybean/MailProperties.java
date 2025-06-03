
package com.velotech.fanselection.propertybean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties{

	String from;

	String postUrl;

	String authorization;

	String bounce_address;

	String address;

	public String getFrom() {

		return from;
	}

	public void setFrom(String from) {

		this.from = from;
	}

	public String getPostUrl() {

		return postUrl;
	}

	public void setPostUrl(String postUrl) {

		this.postUrl = postUrl;
	}

	public String getAuthorization() {

		return authorization;
	}

	public void setAuthorization(String authorization) {

		this.authorization = authorization;
	}

	public String getBounce_address() {

		return bounce_address;
	}

	public void setBounce_address(String bounce_address) {

		this.bounce_address = bounce_address;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress(String address) {

		this.address = address;
	}
	
	
}
