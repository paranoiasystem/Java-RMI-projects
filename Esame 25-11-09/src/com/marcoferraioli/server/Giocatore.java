package com.marcoferraioli.server;

import com.marcoferraioli.client.IClient;

public class Giocatore {
	private IClient id;
	private String username;
	private int credito;
	private boolean puntato;
	private boolean vincitore;
	private int puntata;
	
	public Giocatore(IClient id, String username) {
		this.id = id;
		this.username = username;
		this.credito = 40;
		this.puntata = 0;
	}

	public IClient getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public int getCredito() {
		return credito;
	}

	public void setCredito(int credito) {
		this.credito = credito;
	}

	public boolean isPuntato() {
		return puntato;
	}

	public void setPuntato(boolean puntato) {
		this.puntato = puntato;
	}

	public boolean isVincitore() {
		return vincitore;
	}

	public void setVincitore(boolean vincitore) {
		this.vincitore = vincitore;
	}

	public int getPuntata() {
		return puntata;
	}

	public void setPuntata(int puntata) {
		this.puntata = puntata;
	}
	
}
