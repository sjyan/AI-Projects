package searchAlgorithms;

import searchAlgorithms.CandyLocation.Color;

public abstract class Search {
	
	protected CandyLocation[][] board;
	protected int[][] values;
	protected int rows;
	protected int columns;
	protected Color color = Color.EMPTY;
	protected Coordinate nextMove;
	protected Coordinate bestMove;
	
	public Search() {}
	
	public abstract Coordinate search(CandyLocation[][] board, int depth);
	
	protected void setBoard(CandyLocation[][] board) {
		this.board = board;
	}
	
	protected int checkUtil(Color color) {
		int utility = 0;
		
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (board[i][j].getColor() == color) {
					utility += values[i][j];
				}
			}
		}
		
		return utility;
	}
	
	public void setValues(int[][] values) {
		this.values = values;
	}
	
	public void setSize(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
