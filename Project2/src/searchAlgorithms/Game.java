package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import searchAlgorithms.CandyLocation.Color;

public class Game {
	
	protected CandyLocation[][] grid = new CandyLocation[6][6];
	protected int[][] values = new int[6][6];
	protected Search playerA;
	protected Search playerB;
	protected Coordinate nextMove;
	
	public Game(String fileName, Search A, Search B) throws IOException {
		readFromFile(fileName);
		this.playerA = A;
		this.playerB = B;
		
		
		
		for (int i=0; i < 18; i++) {
			nextMove = playerA.search(Color.BLUE);
			move(nextMove, Color.BLUE);
			nextMove = playerB.search(Color.GREEN);
			move(nextMove, Color.GREEN);
		}
	}
	
	private void move(Coordinate position, Color color) {
		int x = position.getX();
		int y = position.getY();
		grid[x][y].setState(color);
		
		if (checkAdjacent(x, y, color)) {
			flipAdjacent(x, y, color);
		}
	}
	
	private boolean checkAdjacent(int x, int y, Color color) {
		
		if (grid[x-1][y].getState() == color) {
			return true;
		} else if (grid[x+1][y].getState() == color) {
			return true;
		} else if (grid[x][y-1].getState() == color) {
			return true;
		} else if (grid[x][y+1].getState() == color) {
			return true;
		}
		
		return false;
	}
	
	private void flipAdjacent(int x, int y, Color color) {
		
		if (grid[x-1][y].getState() != color &&
				grid[x-1][y].getState() != Color.EMPTY) {
			grid[x-1][y].flip();
		} else if (grid[x+1][y].getState() != color &&
				grid[x+1][y].getState() != Color.EMPTY) {
			grid[x+1][y].flip();
		} else if (grid[x][y-1].getState() != color &&
				grid[x][y-1].getState() != Color.EMPTY) {
			grid[x][y-1].flip();
		} else if (grid[x][y+1].getState() != color &&
				grid[x][y+1].getState() != Color.EMPTY) {
			grid[x][y+1].flip();
		}
	}
	
	public Color findWinner() {
		int green = 0;
		int blue = 0;
		
		for (int i=0; i<6; i++) {
			for (int j=0; j<6; j++) {
				if (grid[i][j].getState() == Color.BLUE) {
					blue += values[i][j];
				} else {
					green += values[i][j];
				}
			}
		}
		
		if (blue > green) {
			return Color.BLUE;
		} else if (blue < green) {
			return Color.GREEN;
		} else {
			return Color.EMPTY;
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
