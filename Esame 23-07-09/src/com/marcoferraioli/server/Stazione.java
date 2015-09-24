package com.marcoferraioli.server;

import java.util.ArrayList;

import com.marcoferraioli.client.IClient;

public class Stazione {
	
	private IClient id;
	private String nickname;
	private Integer temperatura;
	private ArrayList<String> vicini;
	
	public Stazione(IClient id, String nickname) {
		this.id = id;
		this.nickname = nickname;
		this.temperatura  = null;
		this.vicini = new ArrayList<String>();
	}

	public Integer getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}

	public IClient getId() {
		return id;
	}

	public ArrayList<String> getVicini() {
		return vicini;
	}

	public String getNickname() {
		return nickname;
	}
	
	public void addVicino(String nickname){
		vicini.add(nickname);
	}

}
