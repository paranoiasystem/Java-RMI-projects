package com.marcoferraioli.utility;

import java.io.Serializable;

public class Messaggio implements Serializable{
	public String username;
	public String message;
	
	public Messaggio(String username, String message) {
		this.username = username;
		this.message = message;
	}
}
