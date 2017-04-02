package it.polimi.ingsw.tris;

import it.polimi.ingsw.exceptions.*;
import java.util.Random;
import java.util.Scanner;

import javax.sound.midi.Soundbank;

public class MatchSocket {
	private Player currentPlayer;
	private int currentSize;
	private Player[] players;
	private Board board;
	private Scanner scanner;
	public final static int MAX_SIZE = 2;
	
	public MatchSocket() {
		this(MAX_SIZE);
	}
	
	public MatchSocket(int size) {
		this.currentSize = 0;
		this.players = new Player[size];
		this.board = new Board();
		this.scanner = new Scanner(System.in);
	}

	public boolean addPlayer(Player player) {
		if(currentSize == MAX_SIZE)
			return false;
		
		players[currentSize] = player;
		currentSize++;
		return true;
	}
	
	/** TODO: implement choosing symbols automatically **/
	/*public boolean createPlayer(char symbol) {
		if(currentSize == players.length) {
			System.out.println("The room is full");
			return false;
		}
		
		System.out.println("Insert the name of the first player");
		String name = scanner.nextLine();
		while(isNameAlreadyTaken(name)) {
			System.out.println("The name is already taken, choose another one");
			name = scanner.nextLine();
		}
		
		Player player = new Player(name, symbol);
		players[currentSize] = player;
		currentSize++;
		return true;
	}*/
	
	public void chooseRandomPlayer() {
		int randomIndex = new Random().nextInt(currentSize);
		currentPlayer = players[randomIndex];
	}
	
	private boolean isNameAlreadyTaken(String name) {
		for (Player player : players) {
			if(player.getName() == name)
				return true;
		}
		return false;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}
