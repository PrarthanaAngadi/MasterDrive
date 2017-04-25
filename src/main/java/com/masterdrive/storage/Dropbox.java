package com.masterdrive.storage;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.masterdrive.user.User;

@Entity
@Table(name="dropbox")
public class Dropbox {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("dropbox_id")
	private long dropbox_id;
	
	@JsonProperty("email")
	private String email;
	
	@NotNull
	@JsonProperty("access_token")
	private String accessToken;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public void setDropbox_id(long dropbox_id) {
		this.dropbox_id = dropbox_id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getDropbox_id() {
		return dropbox_id;
	}

	public String getEmail() {
		return email;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public User getUser() {
		return user;
	}
	
	
}
