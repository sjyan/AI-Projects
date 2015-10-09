package gameAlgorithms;

public abstract class Player {
	protected int ROWS = 6;
	protected int COLS = 6;
	protected Cell[][] cells;
	protected Color myColor;
	protected Color oppColor;
	
	public Player(Cell[][] board) {
		cells = board;
	}
	
	public void setColor(Color color) {
		this.myColor = color;
		oppColor = (myColor == Color.BLUE) ? Color.GREEN : Color.BLUE;
	}
	
	abstract int[] move();
}
