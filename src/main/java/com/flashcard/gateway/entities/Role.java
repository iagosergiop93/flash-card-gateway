package com.flashcard.gateway.entities;

import java.sql.Timestamp;

public class Role {
	
	private int id;
	
	private String name;
	
	private Timestamp _createdAt;
	
	private Timestamp _updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp get_createdAt() {
		return _createdAt;
	}

	public void set_createdAt(Timestamp _createdAt) {
		this._createdAt = _createdAt;
	}

	public Timestamp get_updatedAt() {
		return _updatedAt;
	}

	public void set_updatedAt(Timestamp _updatedAt) {
		this._updatedAt = _updatedAt;
	}
	
}
