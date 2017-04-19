package com.masterdrive.dropbox;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.masterdrive.util.Status;
import com.masterdrive.util.Status.Code;

@RestController
@RequestMapping("/dropbox")
public class DropboxController {

	private static final String DROP_BOX_APP_KEY = "giklxfjqov76w7r";
	private static final String DROP_BOX_APP_SECRET = "cjx6qjtk6zgiken";
	public Map<String,Object> response;
	
	@RequestMapping(value="/authorize",method = RequestMethod.GET)
	public Map<String,Object> authorize() {
		response = new HashMap<String, Object>();
		DbxAppInfo dbxAppInfo = new DbxAppInfo(DROP_BOX_APP_KEY,DROP_BOX_APP_SECRET );
		DbxRequestConfig dbxRequestConfig = new DbxRequestConfig(
				"JavaDropboxTutorial/1.0", Locale.getDefault().toString());
		DbxWebAuthNoRedirect dbxWebAuthNoRedirect = new DbxWebAuthNoRedirect(
				dbxRequestConfig, dbxAppInfo);
		String authorizeUrl = dbxWebAuthNoRedirect.start();
		response.put("status", Status.create(Code.SUCCESS));
		response.put("url", authorizeUrl);
		return response;
		
	}
}
