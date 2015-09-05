package com.marcoferraioli.server;

import com.marcoferraioli.client.Client;

public class Iscritto {
	
	public Client id;
	public String username;
	public int credito;
	
	public Iscritto(Client id, String username) {
		this.id = id;
		this.username = username;
		this.credito = 0;
	}
}
