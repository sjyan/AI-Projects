package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import searchAlgorithms.CandyLocation.Color;

public class Game {
	
	protected CandyLocation[][] board;
	protected int[][] values;
	protected Search playerA;
	protected Search playerB;
	protected Coordinate nextMove;
	protected int rows;
	protected int columns;
	protected ArrayList<String[]> rowList = new ArrayList<String[]>();
	protected int depth;
	
	public Game(String fileName, Search searchA, Search searchB, int depth) throws IOException {
		readFromFile(fileName);
		
		this.playerA = searchA;
		this.playerB = searchB;
		
		playerA.setValues(values);
		playerB.setValues(values);
		
		playerA.setSize(rows, columns);
		playerB.setSize(rows, columns);
		
		playerA.setColor(Color.BLUE);
		playerB.setColor(Color.GREEN);
		
		this.depth = depth;
		
		boolean turn = true;
		
		printBoard();
		
		for (int i=0; i < rows*columns; i++) {
			if (turn) {
				playerA.setBoard(board);
				nextMove = playerA.search(board, depth);
				move(nextMove, Color.BLUE);
				
				turn = !turn;
			} else {
				playerB.setBoard(board);
				nextMove = playerB.search(board, depth);
				move(nextMove, Color.GREEN);
				
				turn = !turn;
			}
		}
		
	}
	
	private void move(Coordinate position, Color color) {
		int x = position.getX();
		int y = position.getY();
		board[x][y].setColor(color);
		
		if (checkAdjacent(x, y, color)) {
			flipAdjacent(x, y, color);
		}
	}
	
	private boolean checkAdjacent(int x, int y, Color color) {
		
		if (x-1 >= 0 && board[x-1][y].getColor() == color) {
			return true;
			
		} else if (x+1 < board.length && board[x+1][y].getColor() == color) {
			return true;
			
		} else if (y-1 >= 0 && board[x][y-1].getColor() == color) {
			return true;
			
		} else if (y+1 > board[x].length && board[x][y+1].getColor() == color) {
			return true;
		}
		
		return false;
	}
	
	private void flipAdjacent(int x, int y, Color color) {
		
		if 		  ( x-1 >= 0 &&
					board[x-1][y].getColor() != color &&
					board[x-1][y].getColor() != Color.EMPTY	) {
			
			board[x-1][y].flip();
			
		} else if (	x+1 < board.length &&
					board[x+1][y].getColor() != color &&
					board[x+1][y].getColor() != Color.EMPTY	) {
			
			board[x+1][y].flip();
			
		} else if (	y-1 >= 0 &&
					board[x][y-1].getColor() != color &&
					board[x][y-1].getColor() != Color.EMPTY) {
			
			board[x][y-1].flip();
			
		} else if (	y+1 < board[x].length &&
					board[x][y+1].getColor() != color &&
					board[x][y+1].getColor() != Color.EMPTY) {
			
			board[x][y+1].flip();
			
		}
	}
	
	public Color checkWinner() {
		int score = 0;
		
		for (int i=0; i<6; i++) {
		for (int j=0; j<6; j++) {
			if (board[i][j].getColor() == Color.BLUE) {
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
		
		String line = "";
		String[] splitLine;
		
		while ((line = reader.readLine()) != null) {
			splitLine = line.split("\\s+");
			rowList.add(splitLine);
		}
		
		rows = rowList.size();
		columns = rowList.get(0).length;
		
		board = new CandyLocation[rows][columns];
		
		for (int ver=0; ver<rows; ver++) {
			for (int hor=0; hor<columns; hor++) {
				board[ver][hor] = new CandyLocation();
		}
		}
		
		
		
		values = new int[rows][columns];
		
		for (int ver=0; ver<rows; ver++) {
		for (int hor=0; hor<columns; hor++) {
			values[ver][hor] = Integer.parseInt(rowList.get(ver)[hor]);
		}
		}
		
	}
	
	public CandyLocation[][] getBoard() {
		return this.board;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getColumns() {
		return this.columns;
	}
	
	private void printBoard() {
		String colorBoard = "";
		
		
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[row].length; column++) {
				if (board[row][column].getColor() == Color.BLUE) {
					colorBoard += "B ";
				} else if (board[row][column].getColor() == Color.GREEN) {
					colorBoard += "G ";
				} else if (board[row][column].getColor() == Color.EMPTY) {
					colorBoard += "E ";
				}
			}
			
			System.out.println(colorBoard);
			colorBoard = "";
		}
	}
}
