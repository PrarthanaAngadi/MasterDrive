package com.masterdrive.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.masterdrive.util.Status;

@RestController
@RequestMapping(value = "/user")
public class UserControllerImpl implements UserController{

	@Override
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public User signIn(String email, String password) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public User signUp(User user) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping(value = "/invalidate", method = RequestMethod.GET)
	public Status signOut(int userId) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

}
