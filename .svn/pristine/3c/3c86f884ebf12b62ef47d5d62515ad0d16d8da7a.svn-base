
package com.velotech.fanselection.utils;

import java.util.List;

public final class ApplicationResponseUtil {

	/**
	 * 
	 * @param success
	 * @param message
	 * @param dataList
	 * @param total
	 * @return
	 */
	public static ApplicationResponse getResponseToGetData(final boolean success, final String message, final List<?> dataList, final long total) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse.setSuccess(success);
		applicationResponse.setMessage(message);
		applicationResponse.setTotal(total);
		applicationResponse.setData(dataList);
		return applicationResponse;
	}

	/**
	 * 
	 * @param success
	 * @param message
	 * @param data
	 * @return
	 */
	public static ApplicationResponse getResponseToGetData(final boolean success, final String message, final Object data) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse.setSuccess(success);
		applicationResponse.setMessage(message);
		applicationResponse.setData(data);
		return applicationResponse;
	}

	/**
	 * 
	 * @param success
	 * @param message
	 * @param data
	 * @return
	 */
	public static ApplicationResponse getResponseToGetData(final boolean success, final String message) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse.setSuccess(success);
		applicationResponse.setMessage(message);
		return applicationResponse;
	}

	/**
	 * @param isSuccess
	 * @param message
	 * @return
	 */
	public static ApplicationResponse getResponseToCRUD(final boolean isSuccess, final String message) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse.setSuccess(isSuccess);
		applicationResponse.setMessage(message);
		return applicationResponse;
	}

	/**
	 * @param isSuccess
	 * @param message
	 * @return
	 */
	public static ApplicationResponse getResponseToCRUD(final boolean isSuccess, final String message, final Object data) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		applicationResponse.setSuccess(isSuccess);
		applicationResponse.setMessage(message);
		applicationResponse.setData(data);
		return applicationResponse;
	}
}
