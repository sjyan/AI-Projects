package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import searchAlgorithms.CandyLocation.DomainState;

public class Game {
	
	protected CandyLocation[][] grid = new CandyLocation[6][6];
	protected int[][] values = new int[6][6];
	protected Search playerA;
	protected Search playerB;
	protected Coordinate nextMove;
	protected int scoreA;
	protected int scoreB;
	
	public Game(String fileName, Search A, Search B) throws IOException {
		readFromFile(fileName);
		this.playerA = A;
		this.playerB = B;
		
		
		
		for (int i=0; i < 18; i++) {
			nextMove = playerA.search();
			move(nextMove, DomainState.GREEN);
			nextMove = playerB.search();
			move(nextMove, DomainState.BLUE);
		}
	}
	
	private void move(Coordinate position, DomainState state) {
		int x = position.getX();
		int y = position.getY();
		grid[x][y].setState(state);
		
		if (checkAdjacent(x, y, state)) {
			flipAdjacent(x, y, state);
		}
	}
	
	private boolean checkAdjacent(int x, int y, DomainState state) {
		
		if (grid[x-1][y].getState() == state) {
			return true;
		} else if (grid[x+1][y].getState() == state) {
			return true;
		} else if (grid[x][y-1].getState() == state) {
			return true;
		} else if (grid[x][y+1].getState() == state) {
			return true;
		}
		
		return false;
	}
	
	private void flipAdjacent(int x, int y, DomainState state) {
		
		if (grid[x-1][y].getState() != state &&
				grid[x-1][y].getState() != DomainState.EMPTY) {
			grid[x-1][y].flip();
		} else if (grid[x+1][y].getState() != state &&
				grid[x+1][y].getState() != DomainState.EMPTY) {
			grid[x+1][y].flip();
		} else if (grid[x][y-1].getState() != state &&
				grid[x][y-1].getState() != DomainState.EMPTY) {
			grid[x][y-1].flip();
		} else if (grid[x][y+1].getState() != state &&
				grid[x][y+1].getState() != DomainState.EMPTY) {
			grid[x][y+1].flip();
		}
	}
	
	private void readFromFile(String fileName) throws IOException {
		InputStream inputStream = LocalSearch.class.getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		int value;
		
		for (int ver=0; ver<6; ver++) {
			String line = reader.readLine();
			String[] splitArray = line.split("\\s+");
			for (int hor=0; hor<6; hor++) {
				value = Integer.parseInt(splitArray[hor]);
				values[ver][hor] = value;
			}
		}
	}
	
}
