package com.masterdrive.user;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
public class User {
	
	public enum StatusCode {
		NOT_VERIFIED, VERIFIED, RESET_PASSWORD;		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("user_id")
	private long userId;
	
	@NotNull
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	private String lastName;
	
	@NotNull
	private String email;
	
	@NotNull
	@JsonIgnore
	private String password;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusCode status;
	
	@NotNull
	private String verificationCode;
	
	User() {
		this.status = StatusCode.NOT_VERIFIED;
		Random rnd = new Random();
        int digits = 100000 + rnd.nextInt(900000);
        this.verificationCode = String.valueOf(digits); 
	}
	
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = DigestUtils.md5Hex(password);
	}

	/**
	 * @return the status
	 */
	public StatusCode getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusCode status) {
		this.status = status;
	}

	/**
	 * @return the verificationCode
	 */
	public String getVerificationCode() {
		return verificationCode;
	}

	
	
}
