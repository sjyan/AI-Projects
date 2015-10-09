package searchAlgorithms;

import searchAlgorithms.GridLocation.DomainState;

public class CandyLocation {
	
	public enum Color {
		GREEN,
		BLUE,
		EMPTY
	}
	
	Color color;
	
	public CandyLocation() {
		this.color = Color.EMPTY;
	}
	
	public CandyLocation(Color domain) {
		this.color = domain;
	}
	
	public void flip() {
		if (color.equals(Color.GREEN)) {
			this.color = Color.BLUE;
		} else if (color.equals(Color.BLUE)) {
			this.color = Color.GREEN;
		}
	}
	
	public void setColor(Color domain) {
		this.color = domain;
	}
	
	public Color getColor() {
		return this.color;
	}
	
}
