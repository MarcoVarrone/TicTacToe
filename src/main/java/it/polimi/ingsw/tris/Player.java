package it.polimi.ingsw.tris;

import java.io.Serializable;

/** TODO: make it perfectly final **/
public final class Player implements Serializable {
	private char symbol;
	private String name;

	public Player(String name/*, char symbol*/) {
		this.name = name;
		//this.symbol = symbol;
	}
	
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}
	
	public String getName() {
		return name;
	}
	
	
}
