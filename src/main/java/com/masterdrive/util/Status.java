package com.masterdrive.util;

public class Status {

	public enum Code {
		SUCCESS, 
		ERROR, 
		API_ERROR, 
		USER_EXISTS, 
		INVALID_VERIFICATIONCODE, 
		UPDATE_FAILURE,
		USER_NOTFOUND,
		USER_UNVERIFIED
	}

	private Status() {
	}

	private int code;
	private String message;
	private String description;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public static Status create(Code code) {

		try {

			Status status = new Status();

			switch (code) {

			case SUCCESS:
				status.setCode(2000);
				status.setMessage("Success");
				break;

			case ERROR:
				status.setCode(5000);
				status.setMessage("Error");
				break;
				
			case API_ERROR:
				status.setCode(500);
				status.setMessage("Unexpected error in API");
				break;
				
			case USER_EXISTS:
				status.setCode(3000);
				status.setMessage("User already registered");
				break;
				
			case INVALID_VERIFICATIONCODE:
				status.setCode(3100);
				status.setMessage("Invalid Verification Code entered");
				break;
				
			case UPDATE_FAILURE:
				status.setCode(3200);
				status.setMessage("User verification update failed");
				break;
				
			case USER_NOTFOUND:
				status.setCode(3300);
				status.setMessage("User not found");
				break;
				
			case USER_UNVERIFIED:
				status.setCode(3400);
				status.setMessage("User verification is pending");
				break;
			}
			return status;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
