package com.masterdrive.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.Drive;
import com.masterdrive.MasterDriveException;
import com.masterdrive.user.User.StatusCode;
import com.masterdrive.util.Status;
import com.masterdrive.util.Status.Code;

@RestController
@RequestMapping(value = "/user")
public class UserControllerImpl implements UserController {

	@Autowired
	private UserDao userDao;
	
	private User user;	
	private Map<String, Object> response;

	@Override
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public User signIn(String email, String password) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object> signUp(@RequestParam(name = "first_name") String firstName,
			@RequestParam(name = "last_name") String lastName, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password) throws UserException {

		response = new HashMap<>();

		try {
			user = UserFactory.createUser();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPassword(password);			
			userDao.create(user);
			response.put("status", Status.create(Code.SUCCESS));
			response.put("user", user);
			return response;

		} catch (UserException ue) {

			response.put("status", Status.create(Code.ERROR));
			response.put("error", ue);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", Status.create(Code.ERROR));
			response.put("error", new MasterDriveException(Code.API_ERROR));
			
			return response;
		} 

	}

	@RequestMapping(value="/verify", method=RequestMethod.POST) 
	public Map<String, Object> verify(@RequestParam(name="email")String email, 
									  @RequestParam(name="verificationCode")String verificationCode) {
		response = new HashMap<String, Object>();
		try {
			User user = userDao.get(email);
			if(user.getVerificationCode().equals(verificationCode)) {
				user.setStatus(StatusCode.VERIFIED);
				userDao.update(user);
			} else {
				throw new UserException(Code.INVALID_VERIFICATIONCODE, user);
			}
			response.put("status", Status.create(Code.SUCCESS));
			response.put("email", email);
			return response;
		} catch (UserException ue) {
			response.put("status", Status.create(Code.ERROR));
			response.put("error", ue);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", Status.create(Code.ERROR));
			response.put("error", new MasterDriveException(Code.API_ERROR));
			return response;
		} 			                        
	}
	
	@Override
	@RequestMapping(value = "/invalidate", method = RequestMethod.GET)
	public Status signOut(int userId) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

}
