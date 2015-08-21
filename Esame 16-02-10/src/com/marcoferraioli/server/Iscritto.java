package com.marcoferraioli.server;

import com.marcoferraioli.client.Client;

public class Iscritto {
	
	private String username;
	private Client id;
	
	public Iscritto(String username, Client id){
		this.username = username;
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Client getId() {
		return id;
	}

	public void setId(Client id) {
		this.id = id;
	}
	
}
