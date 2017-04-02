package it.polimi.ingsw.tris;

import java.util.Random;
import java.util.Scanner;

public class Match {
	
	private static Player player1, player2, turn;
	private static Board board;
	private static Scanner scanner;
	
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		initializePlayers();
		startMatch();
		board.printStatus();
		
		executeMatch();
		
		scanner.close();
	}
	
	private static void initializePlayers() {
		System.out.println("Insert the name of the first player");
		
		String name1 = scanner.nextLine();
		System.out.println("Insert the symbol of the first player (X or O)");
		String symbol = scanner.nextLine();
		while(!symbol.equals("X") && !symbol.equals("O")) {
			System.out.println("Your symbol cannot be different from X or O, insert your symbol again");
			symbol  = scanner.nextLine();
		}
		// Now I know that symbol1 is either X or O, so I can cast it has a Char
		char symbol1 = symbol.charAt(0);
		player1 = new Player(name1, symbol1);
		
		System.out.println("Insert the name of the second player");
		String name2 = scanner.nextLine();
		char symbol2 = chooseOtherSymbol(symbol1);
		System.out.println("The symbol for the second player is automatically chosen: "+symbol2);
		System.out.println("");
		player2 = new Player(name2, symbol2);
		
	}
	
	private static char chooseOtherSymbol(char symbol) {
		if(symbol == 'X')
			return 'O';
		else
			return 'X';
	}
	
	private static void startMatch() {
		// Select random player to start
		if(new Random().nextInt(2) == 1)
			turn = player1;
		else
			turn = player2;
		
		System.out.println(turn.getName() + " starts");
		
		board = new Board();
			
	}
	
	private static void executeMatch() {
		while(!board.isFull()) {
			Position position;
			while(true) {
				System.out.println("Insert the row where you want to place your symbol");
				int row = scanner.nextInt();
				
				System.out.println("Insert the column where you want to place your symbol");
				int col = scanner.nextInt();
				
				position = new Position(row, col);
				
				try {
					board.add(position, turn);
					break;
				} catch (IndexOutOfBoundsException e) {
					System.err.println("A row or a column exceeds the size of the board");
					continue;
				}
				
				
			}
			
			
			
			if(board.checkWinner(position, turn)) {
				board.printStatus();
				System.out.println("Player " + turn.getName() + " wins!");
				break;
			}
			
			if(turn == player1)
				turn = player2;
			else
				turn = player1;
			
			System.out.println("");
			System.out.println(turn.getName() + "'s turn:");
			board.printStatus();
		}
	}

}
