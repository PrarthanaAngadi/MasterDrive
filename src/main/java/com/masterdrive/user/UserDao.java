package com.masterdrive.user;

import com.masterdrive.MasterDriveDao;
import com.masterdrive.MasterDriveException;

public interface UserDao extends MasterDriveDao<User>{

	/**
	 * Get User from email
	 * 
	 * @param email
	 * 
	 * @return user object
	 * 
	 * @throws UserException
	 */
	public User get(String email) throws MasterDriveException;
	
}
