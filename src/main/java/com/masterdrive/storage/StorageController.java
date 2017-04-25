package com.masterdrive.storage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface StorageController {

	public static final String RESPONSE_TYPE = "response_type=code";
	public static final String GRANT_TYPE = "authorization_code";
	
	
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException, JSONException;

	public Map<String, Object> authorize(String email);
	
	 public Map<String, Object> getFolder(String accessToken, String path);
}
