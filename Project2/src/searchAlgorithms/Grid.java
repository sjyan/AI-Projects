package searchAlgorithms;

import searchAlgorithms.GridLocation.DomainState;

public class Grid {

	private GridLocation[][] gridArray;
	private int numFriends;
	private int numTrees;
	private int currentHeuristic;
	
	public Grid(int numFriends, int numTrees, int[][] treeLocations) {
		this.numFriends = numFriends;
		this.numTrees = numTrees;
		this.gridArray = new GridLocation[this.numFriends][this.numFriends];
		initializeGrid(numFriends);
		addTreesToGrid(treeLocations);
		addFriendsToGridRandom();
	}

	private void addTreesToGrid(int[][] treeLocations) {
		for (int i = 0; i < this.numTrees; i++) {
			gridArray[treeLocations[i][0]][treeLocations[i][1]].setState(DomainState.TREE);
		}
	}

	private void initializeGrid(int numFriends) {
		for (int i = 0; i < numFriends; i++) {
			for (int j = 0; j < numFriends; j++) {
				gridArray[i][j] = new GridLocation();
			}
		}
	}
	
	private void addFriendsToGridRandom() {
		for (int i = 0; i < numFriends; i++) {
			int randomLocation = (int) (Math.random() * numFriends);
			while (gridArray[randomLocation][i].getState() != DomainState.EMPTY) {
				randomLocation = (int) (Math.random() * numFriends);
			}
			gridArray[randomLocation][i].setState(DomainState.FRIEND);
		}
	}
	public GridLocation[][] getGrid() {
		return gridArray;
	}
	
	public int getNumTrees() {
		return numTrees;
	}
	
	public int getNumFriends() {
		return numFriends;
	}
	
	public int getCurrentHeuristic() {
		return currentHeuristic;
	}
	
}
