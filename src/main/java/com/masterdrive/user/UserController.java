package com.masterdrive.user;

import com.masterdrive.util.Status;

public interface UserController {
	
	/**
	 * Signs in the user into MasterDrive
	 * 
	 * @param email Email address of the User
	 * @param password Password of the User
	 * 
	 * @return user 
	 * 
	 * @throws UserException when User cannot be authenticated
	 */
	public User signIn(String email, String password) throws UserException;
	
	/**
	 * Creates a new user account in MasterDrive
	 * 
	 * @param user object containing all user information
	 * 
	 * @return user 
	 * 
	 * @throws UserException when User already exists
	 */
	public User signUp(User user) throws UserException;
	
	/**
	 * Signs out the user and closes the current session
	 * 
	 * @param userId unique id of the user
	 * 
	 * @return status object containing success or failure
	 * 
	 * @throws UserException when no active session exists for the user
	 */
	public Status signOut(int userId) throws UserException;

}
