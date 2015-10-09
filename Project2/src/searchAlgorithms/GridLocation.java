package searchAlgorithms;

public class GridLocation {
	
	public enum DomainState {
		FRIEND, 
		TREE,
		EMPTY
	}
	
	DomainState state;
	
	public GridLocation() {
		this.state = DomainState.EMPTY;
	}
	
	public GridLocation(DomainState domain) {
		this.state = domain;
	}
	
	public void setState(DomainState state) {
		this.state = state;
	}
	
	public DomainState getState() {
		return this.state;
	}
}