package com.marcoferraioli.server;

import com.marcoferraioli.client.Client;

public class Iscritto {
	public String username;
	public Client id;
	public int punteggio;
	
	public Iscritto(Client id, String username){
		this.id = id;
		this.username = username;
		this.punteggio = 0;
	}
}
