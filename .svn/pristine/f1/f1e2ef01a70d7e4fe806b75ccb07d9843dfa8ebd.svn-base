
package com.velotech.fanselection.utils;

/**
 * This class is use for User defined Exception
 *
 */
public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8712200318494082210L;

	private String errorCode;

	public AppException() {
		super();
	}

	public AppException(String message) {
		super(message);  
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public AppException(String message, Throwable cause, String errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {

		return errorCode;
	}
}
