package com.masterdrive.storage;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.masterdrive.MasterDriveException;
import com.masterdrive.user.User;
import com.masterdrive.user.UserDao;
import com.masterdrive.user.UserException;
import com.masterdrive.user.User.StatusCode;
import com.masterdrive.util.Status;
import com.masterdrive.util.Status.Code;

@RestController
@RequestMapping(value="/dropbox")
public class DropboxController implements StorageController{

	private Map<String, Object> response;
	
	private User user;
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DropboxDao dropboxDao;
	
	@Override
	@RequestMapping(value="/authorize", method=RequestMethod.POST)
	public Map<String, Object> authorize(String email) {
		response = new HashMap<String, Object>();		
		StringBuffer url = new StringBuffer();
		String domain = "https://www.dropbox.com/1/oauth2/authorize?";
		String client_id = "&client_id="+env.getProperty("dropbox.client_id");
		String redirect_uri = "&redirect_uri="+env.getProperty("dropbox.redirect_uri");
		String state = "&state="+email;
		url.append(domain).append(DropboxController.RESPONSE_TYPE).append(state).
		append(client_id).append(redirect_uri);
		
		response.put("status", Status.create(Code.SUCCESS));
		response.put("url", url);
		return response;
		
	}


	@Override
	@RequestMapping(value="/authenticate", method=RequestMethod.GET)
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException, JSONException{
		
		String url = "https://api.dropboxapi.com/1/oauth2/token";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("grant_type", DropboxController.GRANT_TYPE));
		params.add(new BasicNameValuePair("code", request.getParameter("code")));
		params.add(new BasicNameValuePair("client_id", env.getProperty("dropbox.client_id")));
		params.add(new BasicNameValuePair("client_secret", env.getProperty("dropbox.client_secret")));
		params.add(new BasicNameValuePair("redirect_uri", env.getProperty("dropbox.redirect_uri")));
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		httpPost.setHeader("Accept", "JSON");
		CloseableHttpResponse response2 = client.execute(httpPost);
	    HttpEntity entity = response2.getEntity();
	    String responseString = EntityUtils.toString(entity, "UTF-8");
		JSONObject json = new JSONObject(responseString);
		
		User user = null;
		try {
			user = userDao.get(request.getParameter("state"));
			if(user == null){
				throw new UserException(Code.USER_NOTFOUND, user);
			}	
		} catch (UserException ue) {
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
		Dropbox dropbox = DropboxFactory.createDropbox();
		dropbox.setAccessToken(json.get("access_token").toString());
		dropbox.setUser(user);
		dropbox.setEmail("");
		user.getDropboxAccounts().add(dropbox);
		try {
			dropboxDao.create(dropbox);
			userDao.update(user);
		} catch (MasterDriveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	@RequestMapping(value="/getFolder", method=RequestMethod.POST)
	public Map<String, Object> getFolder(String accessToken,String path)  {
		response = new HashMap<>();
		JSONObject json;
		try{
			String url = "https://api.dropboxapi.com/2/files/list_folder";
			StringEntity params = new StringEntity("{"
					//+ "\"path\": \"\","
					+ "\"path\":" + "\"" + path + "\"" + ","
					+ "\"recursive\": false,"
					+ "\"include_media_info\": false,"
					+ "\"include_deleted\": false,"
					+ "\"include_has_explicit_shared_members\": false}");
			json=getResponse(url, params, accessToken);
			JSONArray jsonArray = json.getJSONArray("entries");
			while(json.get("has_more").toString().equals("true")) {
				 url = "https://api.dropboxapi.com/2/files/list_folder/continue";
				 String cursor = "\"cursor\":\""+json.get("cursor")+"\"";
				 StringEntity cursorParams = new StringEntity(cursor);
				 json = getResponse(url, cursorParams, accessToken);
				 int j = jsonArray.length();
				 JSONArray entries = json.getJSONArray("entries");
				 for(int i=0; i < entries.length(); i++){
					 jsonArray.put(j, entries.get(i));
					 j++;
				 }
			}
			response.put("status", Status.create(Code.SUCCESS));
			ArrayList<HashMap<String, String>> details = new ArrayList<>();
			for(int i = 0;i<jsonArray.length();i++){
				HashMap<String, String> values = new HashMap<>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				values.put("tag", jsonObject.getString(".tag"));
				values.put("name",jsonObject.getString("name"));
				values.put("path_lower",jsonObject.getString("path_lower"));
				values.put("path_display", jsonObject.getString("path_display"));
				values.put("id", jsonObject.getString("id"));
				details.add(values);
			}
			response.put("entries", details);
		}
		catch(Exception e){
			e.printStackTrace();
			response.put("status", Status.create(Code.ERROR));
			response.put("error", new MasterDriveException(Code.API_ERROR));
		}
		return response;
	}

	public JSONObject getResponse(String url, StringEntity params, String accessToken) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(params);
		httpPost.setHeader("Content-Type", "application/json");
		String authorization = "Bearer "+accessToken;
		httpPost.setHeader("Authorization",authorization);
		CloseableHttpResponse response2 = client.execute(httpPost);
	    HttpEntity entity = response2.getEntity();
	    String responseString = EntityUtils.toString(entity, "UTF-8");
	    JSONObject json = new JSONObject(responseString);
		return json;
	}
	
	
	
}
