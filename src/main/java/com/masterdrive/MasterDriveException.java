package com.masterdrive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.masterdrive.util.Status;
import com.masterdrive.util.Status.Code;

@JsonIgnoreProperties({"stackTrace","message", "localizedMessage", "suppressed","cause"})
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
