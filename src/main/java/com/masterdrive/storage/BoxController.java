package com.masterdrive.storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.masterdrive.util.Status;
import com.masterdrive.util.Status.Code;

@RestController
@RequestMapping(value="/box")
public class BoxController{

private Map<String, Object> response;
	
	@Autowired
	private Environment env;
	
	//@Override
	@RequestMapping(value="/authorize", method=RequestMethod.GET)
	public Map<String, Object> authorize(String email) {
		response = new HashMap<String, Object>();
		
		StringBuffer url = new StringBuffer();
		String domain = "https://account.box.com/api/oauth2/authorize?";
		String client_id = "&client_id="+env.getProperty("box.client_id");
		String redirect_uri = "&redirect_uri="+env.getProperty("box.redirect_uri");
		String state = "&state="+email;
		url.append(domain).append(DropboxController.RESPONSE_TYPE).append(state).
		append(client_id).append(redirect_uri);
		
		response.put("status", Status.create(Code.SUCCESS));
		response.put("url", url);
		return response;
	}

	
	

	

	
	

	

	

	

	
}
