package com.marcoferraioli.server;

import com.marcoferraioli.client.Client;

public class Iscritto {
	
	public Iscritto(Client id, String username){
		this.id = id;
		this.username = username;
		this.felice = false;
	}
		
	public Client getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isFelice() {
		return felice;
	}

	public void setFelice(boolean felice) {
		this.felice = felice;
	}



	private Client id;
	private String username;
	private boolean felice; 
}
