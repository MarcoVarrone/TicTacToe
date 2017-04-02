package it.polimi.ingsw.tris;

public class Player {
	private char symbol;
	private String name;

	public Player(String name, char symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}
	
	public String getName() {
		return name;
	}
	
	
}
