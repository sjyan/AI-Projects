package searchAlgorithms;

public class Coordinate {
	private int x;
	private int y;
	private int utility;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setUtility(int utility) {
		this.utility = utility;
	}
	
	public int getUtility() {
		return this.utility;
	}
}
