package com.masterdrive.util;

public class MasterDriveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6794475594239241084L;

	private Status status;

	public MasterDriveException(Status.Code code) {
		status = Status.create(code);
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

}
