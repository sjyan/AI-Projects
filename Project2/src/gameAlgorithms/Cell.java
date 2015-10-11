package gameAlgorithms;

public class Cell {
	private int value;
	private Color color;
	private int row;
	private int col;
	
	public Cell(int value, int row, int col) {
		this.value = value;
		this.row = row;
		this.col = col;
		this.color = Color.EMPTY;
	}
	
	protected int getValue() {
		return value;
	}
	
	protected Color getColor() {
		return color;
	}
	
	protected void setColor(Color color) {
		this.color = color;
	}
	
	protected void printCoordinate() {
		System.out.println((char)(col + 65) + "" + (row + 1));
	}
	
	protected int getRow() {
		return row;
	}
	
	protected int getCol() {
		return col;
	}
}
