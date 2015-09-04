package com.marcoferraioli.utility;

import java.io.Serializable;

public class Messaggio implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String username;
	public String messaggio;
	
	public Messaggio(String username, String messaggio){
		this.username = username;
		this.messaggio = messaggio;
	}
}
