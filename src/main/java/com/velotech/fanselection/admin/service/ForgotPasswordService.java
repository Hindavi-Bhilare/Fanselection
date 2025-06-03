package com.velotech.fanselection.admin.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.velotech.fanselection.utils.ApplicationResponse;

public interface ForgotPasswordService {
	public ApplicationResponse getRecords(String requestPayload) throws JsonParseException, JsonMappingException, IOException;
}
