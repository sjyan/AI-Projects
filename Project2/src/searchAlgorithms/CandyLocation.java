package searchAlgorithms;

import searchAlgorithms.GridLocation.DomainState;

public class CandyLocation {
	
	public enum DomainState {
		GREEN,
		BLUE,
		EMPTY
	}
	
	DomainState state;
	
	public CandyLocation() {
		this.state = DomainState.EMPTY;
	}
	
	public CandyLocation(DomainState domain) {
		this.state = domain;
	}
	
	public void flip() {
		if (state.equals(DomainState.GREEN)) {
			this.state = DomainState.BLUE;
		} else if (state.equals(DomainState.BLUE)) {
			this.state = DomainState.GREEN;
		}
	}
	
	public void setState(DomainState domain) {
		this.state = domain;
	}
	
	public DomainState getState() {
		return this.state;
	}
	
}
