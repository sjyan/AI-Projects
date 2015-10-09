package gameAlgorithms;

public class Cell {
	private int value;
	private Color color;
	
	public Cell(int value) {
		this.value = value;
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
}
