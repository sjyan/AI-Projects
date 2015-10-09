package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import searchAlgorithms.CandyLocation.Color;

public class Game {
	
	protected CandyLocation[][] grid;
	protected int[][] values;
	protected Search playerA;
	protected Search playerB;
	protected Coordinate nextMove;
	protected int rows = 0;
	protected int columns;
	
	public Game(String fileName, Search searchA, Search searchB) throws IOException {
		readFromFile(fileName);
		this.playerA = searchA;
		this.playerB = searchB;
		
		playerA.setValues(values);
		playerB.setValues(values);
		
		for (int i=0; i < 18; i++) {
			playerA.setBoard(grid);
			nextMove = playerA.search(Color.BLUE);
			move(nextMove, Color.BLUE);
			
			playerB.setBoard(grid);
			nextMove = playerB.search(Color.GREEN);
			move(nextMove, Color.GREEN);
		}
	}
	
	private void move(Coordinate position, Color color) {
		int x = position.getX();
		int y = position.getY();
		grid[x][y].setColor(color);
		
		if (checkAdjacent(x, y, color)) {
			flipAdjacent(x, y, color);
		}
	}
	
	private boolean checkAdjacent(int x, int y, Color color) {
		
		if (grid[x-1][y].getColor() == color) {
			return true;
		} else if (grid[x+1][y].getColor() == color) {
			return true;
		} else if (grid[x][y-1].getColor() == color) {
			return true;
		} else if (grid[x][y+1].getColor() == color) {
			return true;
		}
		
		return false;
	}
	
	private void flipAdjacent(int x, int y, Color color) {
		
		if (grid[x-1][y].getColor() != color &&
				grid[x-1][y].getColor() != Color.EMPTY) {
			grid[x-1][y].flip();
		} else if (grid[x+1][y].getColor() != color &&
				grid[x+1][y].getColor() != Color.EMPTY) {
			grid[x+1][y].flip();
		} else if (grid[x][y-1].getColor() != color &&
				grid[x][y-1].getColor() != Color.EMPTY) {
			grid[x][y-1].flip();
		} else if (grid[x][y+1].getColor() != color &&
				grid[x][y+1].getColor() != Color.EMPTY) {
			grid[x][y+1].flip();
		}
	}
	
	public Color winnerWinnerChickenDinner() {
		int score = 0;
		
		for (int i=0; i<6; i++) {
			for (int j=0; j<6; j++) {
				if (grid[i][j].getColor() == Color.BLUE) {
					score += values[i][j];
				} else {
					score -= values[i][j];
				}
			}
		}
		
		if (score > 0) {
			return Color.BLUE;
		} else if (score < 0) {
			return Color.GREEN;
		} else {
			return Color.EMPTY;
		}
	}
	
	private void readFromFile(String fileName) throws IOException {
		InputStream inputStream = LocalSearch.class.getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		int value;
		
		String firstLine = reader.readLine();
		String[] splitArray = firstLine.split("\\s+");
		columns = splitArray.length;
		rows += 1;
		
		grid = new CandyLocation[splitArray.length][splitArray.length];
		values = new int[splitArray.length][splitArray.length];
		
		for (int hor=0; hor<splitArray.length; hor++) {
			value = Integer.parseInt(splitArray[hor]);
			values[0][hor] = value;
		}
		
		
		for (int ver=1; ver<splitArray.length; ver++) {
			String line = reader.readLine();
			splitArray = line.split("\\s+");
			for (int hor=0; hor<splitArray.length; hor++) {
				value = Integer.parseInt(splitArray[hor]);
				values[ver][hor] = value;
			}
		}
	}
	
}
