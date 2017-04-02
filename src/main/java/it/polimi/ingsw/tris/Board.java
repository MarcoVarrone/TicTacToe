package it.polimi.ingsw.tris;
import it.polimi.ingsw.exceptions.*;

import java.util.HashMap;
import java.util.Map;

public class Board {
	private Map<Position, Player> map = new HashMap<>();
	private static final int DIM = 3;

	public void add(Position position, Player player) throws IndexOutOfBoundsException, PositionNotAvailable {
		validatePosition(position);

		map.put(position, player);

		checkWinner(position, player);
	}

	private void validatePosition(Position position) throws IndexOutOfBoundsException, PositionNotAvailable {
		boolean invalidCol = position.getCol() < 0 || position.getCol() >= DIM;
		boolean invalidRow = position.getRow() < 0 || position.getRow() >= DIM;
		if (invalidCol || invalidRow)
			throw new IndexOutOfBoundsException();
		
		if(getPlayerInPosition(position.getRow(), position.getCol()) != null)
			throw new PositionNotAvailable();
	}

	public boolean checkWinner(Position position, Player player) {
		if (checkVertical(position, player))
			return true;

		if (checkHorizontal(position, player))
			return true;

		if (position.inMainDiagonal())
			if (checkMainDiagonal(player))
				return true;

		if (position.inSecondaryDiagonal(DIM))
			if (checkSecondaryDiagonal(player))
				return true;

		return false;

	}

	private boolean checkVertical(Position position, Player player) {
		int col = position.getCol();
		for (int i = 0; i < DIM; i++) {
			Player newPlayer = getPlayerInPosition(col, i);
			if (newPlayer != player || newPlayer == null)
				return false;
		}
		return true;
	}

	private boolean checkHorizontal(Position position, Player player) {
		int row = position.getRow();
		for (int i = 0; i < DIM; i++) {
			Player newPlayer = getPlayerInPosition(i, row);
			if (newPlayer != player || newPlayer == null)
				return false;
		}
		return true;
	}

	private boolean checkMainDiagonal(Player player) {
		for (int i = 0; i < DIM; i++) {
			Player newPlayer = getPlayerInPosition(i, i);
			if (newPlayer != player || newPlayer == null)
				return false;
		}
		return true;
	}

	private boolean checkSecondaryDiagonal(Player player) {
		for (int i = 0; i < DIM; i++) {
			Player newPlayer = getPlayerInPosition(i, DIM - i);
			if (newPlayer != player || newPlayer == null)
				return false;
		}
		return true;
	}

	public void printStatus() {
		for (int row = 0; row < DIM; ++row) {
			for (int col = 0; col < DIM; ++col) {
				Player player = getPlayerInPosition(row, col);
				if (player == null)
					System.out.print("   ");
				else
					System.out.print(" " + player.getSymbol() + " ");
				if (col != DIM - 1) {
					System.out.print("|"); // print vertical partition
				}
			}
			System.out.println();
			if (row != DIM - 1) {
				System.out.println("-----------"); // print horizontal partition
			}
		}
		System.out.println();
	}

	private Player getPlayerInPosition(int row, int col) {
		Position position = new Position(row, col);
		return map.get(position);
	}

	public boolean isFull() {
		for (int row = 0; row < DIM; ++row) {
			for (int col = 0; col < DIM; ++col) {
				Player player = getPlayerInPosition(row, col);
				if (player == null)
					return false;
			}
		}

		return true;
	}

	public int getDim() {
		return DIM;
	}
}
