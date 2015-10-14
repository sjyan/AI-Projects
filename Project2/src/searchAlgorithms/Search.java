package searchAlgorithms;

import searchAlgorithms.CandyLocation.Color;

public abstract class Search {
	
	protected int[][] values;
	protected int rows;
	protected int columns;
	protected CandyLocation.Color playerColor;
	protected boolean max;
	protected int nodes;
	protected final int MINIMUM = Integer.MIN_VALUE;
	protected final int MAXIMUM = Integer.MAX_VALUE;
	
	public Search() {}
	
	public abstract Coordinate search(CandyLocation[][] board, int depth, Color playerColor, boolean max, int maxDepth);
	
	//Adds points of given color and subtracts opposing color
	protected int checkUtil(CandyLocation[][] board) {
		int utility = 0;
		
		for (int i=0; i<rows; i++) {
		for (int j=0; j<columns; j++) {
			if (board[i][j].getColor() == playerColor) {
				utility += values[i][j];
			} else if (board[i][j].getColor() != Color.EMPTY) {
				utility -= values[i][j];
			}
		}
		}
		
		return utility;
	}
	
	//Places candy onto board
	protected void move(CandyLocation[][] board, int x, int y, Color color) {
		board[x][y].setColor(color);
		
		if (checkAdjacent(board, x, y, color)) {
			flipAdjacent(board, x, y, color);
		}
	}
	
	//Checks if any of the same color are adjacent
	protected boolean checkAdjacent(CandyLocation[][] board, int x, int y, Color color) {
		
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
	protected void flipAdjacent(CandyLocation[][] board, int x, int y, Color color) {
		
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
	
	//Creates deep copy of board to manipulate
	protected CandyLocation[][] copyBoard(CandyLocation[][] board) {
		CandyLocation[][] copyBoard = new CandyLocation[rows][columns];
		
		for (int i=0; i<rows; i++) {
		for (int j=0; j<columns; j++) {
			copyBoard[i][j] = new CandyLocation(board[i][j].getColor());
		}
		}
		
		
		return copyBoard;
	}
	
	//Sets player color
	public void setColor(Color color) {
		this.playerColor = color;
	}
	
	//Sets values of game
	public void setValues(int[][] values) {
		this.values = values;
	}
	
	//Sets rows and columns of game
	public void setSize(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}
	
	//Returns total nodes from game
	public int getNodes() {
		return this.nodes;
	}
	
	//Determines if search is AlphaBeta or MiniMax
	public abstract boolean getSearch();
}
