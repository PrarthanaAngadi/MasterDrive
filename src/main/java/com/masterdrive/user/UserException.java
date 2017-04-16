package com.masterdrive.user;

import com.masterdrive.MasterDriveException;
import com.masterdrive.util.Status.Code;

public class UserException extends MasterDriveException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8402991771535353751L;

	private User user;
	public UserException(Code code, User user) {
		super(code);
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	
}
