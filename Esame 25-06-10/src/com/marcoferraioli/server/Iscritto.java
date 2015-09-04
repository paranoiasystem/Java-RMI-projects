package com.marcoferraioli.server;

import java.util.ArrayList;

import com.marcoferraioli.client.Client;

public class Iscritto {
	public Client id;
	public String username;
	public String stato;
	public ArrayList<String> commenti = new ArrayList<String>();
	
	public Iscritto(Client id, String username){
		this.id = id;
		this.username = username;
		this.stato = "Mi sono appena iscritto";
	}
	
	public void addCommento(String username, String commento){
		this.commenti.add(username + ": " + commento);
	}
	
}
