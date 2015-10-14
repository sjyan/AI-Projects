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
	protected int score;
	protected long timeA;
	protected long timeB;
	protected int scoreBlue;
	protected int scoreGreen;
	
	public Game(String fileName, Search searchA, Search searchB, int depth_1, int depth_2) throws IOException {
		readFromFile(fileName);
		
		this.playerA = searchA;
		this.playerB = searchB;
		
		playerA.setValues(values);
		playerB.setValues(values);
		
		playerA.setSize(rows, columns);
		playerB.setSize(rows, columns);
		
		playerA.setColor(Color.BLUE);
		playerB.setColor(Color.GREEN);
		
		boolean turn = true;
		int maxDepth = rows*columns -1;
		
		if (depth_1 > maxDepth) {depth_1 = maxDepth;}
		if (depth_2 > maxDepth) {depth_2 = maxDepth;}

		for (int i=0; i < rows*columns; i++) {
			if (turn) {
				long startTime = System.currentTimeMillis();
				nextMove = playerA.search(board, depth_1, Color.BLUE, true, maxDepth);
				System.out.println("Blue Moves: " + (char)(nextMove.getY()+65) + (nextMove.getX()+1));
				
				long endTime = System.currentTimeMillis();
				
				timeA += ((endTime - startTime));
				
				move(nextMove, Color.BLUE);
				
				turn = !turn;
				maxDepth --;
				
			} else {
				long startTime = System.currentTimeMillis();
				nextMove = playerB.search(board, depth_2, Color.GREEN, true, maxDepth);
				System.out.println("Green Moves: " + (char)(nextMove.getY()+65) + (nextMove.getX()+1));
				
				long endTime = System.currentTimeMillis();

				timeB += ((endTime - startTime));
				
				move(nextMove, Color.GREEN);
				
				turn = !turn;
				maxDepth--;
			}
		}
		
	}
	
	//Places move onto board based on color
	private void move(Coordinate position, Color color) {
		int x = position.getX();
		int y = position.getY();
		board[x][y].setColor(color);
		
		if (checkAdjacent(x, y, color)) {
			flipAdjacent(x, y, color);
		}
	}
	
	//Checks if any of the same color are adjacent
	private boolean checkAdjacent(int x, int y, Color color) {
		
		if (x-1 >= 0 && board[x-1][y].getColor() == color) {
			return true;
			
		} else if (x+1 < board.length && board[x+1][y].getColor() == color) {
			return true;
			
		} else if (y-1 >= 0 && board[x][y-1].getColor() == color) {
			return true;
			
		} else if (y+1 < board[x].length && board[x][y+1].getColor() == color) {
			return true;
		}
		
		return false;
	}
	
	//Flips opposing colors to player color
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
	
	//Checks winner and adds to each players score
	public Color checkWinner() {
		int score = 0;
		
		for (int i=0; i<rows; i++) {
		for (int j=0; j<columns; j++) {
			if (board[i][j].getColor() == Color.BLUE) {
				score += values[i][j];
				this.scoreBlue += values[i][j];
			} else {
				score -= values[i][j];
				this.scoreGreen += values[i][j];
			}
		}
		}
		
		if (score > 0) {
			this.score = score;
			return Color.BLUE;
		} else if (score < 0) {
			this.score = -score;
			return Color.GREEN;
		} else {
			return Color.EMPTY;
		}
	}
	
	//Converts text file to 2d arrays for colors and values
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
	
	//Returns current state of board
	public CandyLocation[][] getBoard() {
		return this.board;
	}
	
	//Returns # of rows
	public int getRows() {
		return this.rows;
	}
	
	//Returns # of columns
	public int getColumns() {
		return this.columns;
	}
	
	//Returns score differential
	public int getScore() {
		return this.score;
	}
	
	//Prints out current state of board. Used for testing
	private void printBoard() {
		String colorBoard = "";
		
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j < columns; j++) {
				if (board[i][j].getColor() == Color.BLUE) {
					colorBoard += "B ";
				} else if (board[i][j].getColor() == Color.GREEN) {
					colorBoard += "G ";
				} else if (board[i][j].getColor() == Color.EMPTY) {
					colorBoard += "E ";
				}
			}
			
			
			System.out.println(colorBoard);
			colorBoard = "";
		}
	}
	
	//Returns blue's score
	public int getBlue() {
		return this.scoreBlue;
	}
	
	//Returns green's score
	public int getGreen() {
		return this.scoreGreen;
	}
	
	//Returns blue's average time per move
	public long getBlueTime() {
		double moves = Math.ceil(rows*columns/2);
		
		return (long) (this.timeA/moves);
	}
	
	//Returns green's average time per move
	public long getGreenTime() {
		double moves = Math.floor(rows*columns/2);
		
		return (long) (this.timeA/moves);
	}
	
	//Returns blue's total nodes opened
	public int getBlueNodes() {
		return playerA.getNodes();
	}
	
	//Returns green's total nodes opened
	public int getGreenNodes() {
		return playerB.getNodes();
	}
	
	//Returns blue's average nodes opened
	public double getBlueAvg() {
		double moves = Math.ceil(rows*columns/2);
		
		return (playerA.getNodes()/moves);
	}
	
	//Returns green's average nodes opened
	public double getGreenAvg() {
		double moves = Math.floor(rows*columns/2);
		
		return (playerB.getNodes()/moves);
	}
}
