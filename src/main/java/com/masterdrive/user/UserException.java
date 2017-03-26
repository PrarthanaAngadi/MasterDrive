package com.masterdrive.user;

import com.masterdrive.util.MasterDriveException;
import com.masterdrive.util.Status.Code;

public class UserException extends MasterDriveException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8402991771535353751L;

	private User user;

	public UserException(Code code) {
		super(code);
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
