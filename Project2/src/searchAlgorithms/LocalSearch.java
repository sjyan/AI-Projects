package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import searchAlgorithms.GridLocation.DomainState;

public class LocalSearch {

	int numFriends, numTrees;
	GridLocation[][] grid;
	
	public LocalSearch() throws IOException {
		readFromFile("../forests/input.txt");
		addFriendsToGrid();
		printGrid();
	}
	
	private void addFriendsToGrid() {
		for (int i = 0; i < numFriends; i++) {
			int randomLocation = (int) (Math.random() * numFriends);
			while (grid[randomLocation][i].getState() != DomainState.EMPTY) {
				randomLocation = (int) (Math.random() * numFriends);
			}
			grid[randomLocation][i].setState(DomainState.FRIEND);
		}
	}
	
	private void readFromFile(String fileName) throws IOException {
		InputStream inputStream = LocalSearch.class.getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		String firstLine = reader.readLine();
		String[] splitArray = firstLine.split("\\s+");
		this.numFriends = Integer.parseInt(splitArray[0]);
		this.numTrees = Integer.parseInt(splitArray[1]);
		
		this.grid = new GridLocation[numFriends][numFriends];
		for (int i = 0; i < numFriends; i++) {
			for (int j = 0; j < numFriends; j++) {
				grid[i][j] = new GridLocation();
			}
		}
		
		for (int i = 0; i < numTrees; i++) {
			String nextTree = reader.readLine();
			splitArray = nextTree.split("\\s+");
			// the sample input is 1-indexed
			this.grid[Integer.parseInt(splitArray[0]) - 1][Integer.parseInt(splitArray[1]) - 1].setState(DomainState.TREE);
		}
	}
	
	public void printGrid() {
		int length = grid.length;
		for (int i = 0; i < length; i++) {
			System.out.println();
			for (int j = 0; j < length; j++) {
				if (grid[i][j].getState()== DomainState.FRIEND) {
					System.out.print("F");
				} else if (grid[i][j].getState() == DomainState.TREE) {
					System.out.print("T");
				} else {
					System.out.print(" ");
				}
			}
		}
	}
}