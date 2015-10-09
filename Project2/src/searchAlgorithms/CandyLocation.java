package searchAlgorithms;

import searchAlgorithms.GridLocation.DomainState;

public class CandyLocation {
	
	public enum Color {
		GREEN,
		BLUE,
		EMPTY
	}
	
	Color state;
	
	public CandyLocation() {
		this.state = Color.EMPTY;
	}
	
	public CandyLocation(Color domain) {
		this.state = domain;
	}
	
	public void flip() {
		if (state.equals(Color.GREEN)) {
			this.state = Color.BLUE;
		} else if (state.equals(Color.BLUE)) {
			this.state = Color.GREEN;
		}
	}
	
	public void setState(Color domain) {
		this.state = domain;
	}
	
	public Color getState() {
		return this.state;
	}
	
}
