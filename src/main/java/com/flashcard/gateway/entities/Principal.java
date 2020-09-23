package com.flashcard.gateway.entities;

public class Principal {
	
	private long id;
	private String username;
	private Role role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Principal [id=" + id + ", username=" + username
				+ ", role=" + role + "]";
	}
	
}
