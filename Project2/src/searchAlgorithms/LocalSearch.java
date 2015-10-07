package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import searchAlgorithms.GridLocation.DomainState;

public class LocalSearch {

	int numFriends, numTrees;
	Grid grid;
	
	public LocalSearch() throws IOException {
		readFromFileToGrid("../forests/input.txt");
		steepestAscentHillClimbingSearch();
		printGrid();
	}
	
	/*
	 * complete state formulation
	 * heuristic = sum of all friends each friend can see (sum of all pairs of friends who can see each other * 2)
	 * 2 methods: iterate through columns systematically, or choose random column
	 * terminate when: the BEST value of the next state is less than or equal to to the current value of the state. i.e. we cannot go up anymore
	 * if it's equal, that's a plateau? perhaps we will allow sideways movements in plateaus
	 * but don't let it reach an infinite loop! set a limit to how many sideways movements it can go before terminating
	 * this should increase the chance of finding the optimal solution (h = 0), but the algorithm will take longer on average
	 * variants of hill climbing: 1. steepest ascent - greedy, choose best next state 
	 * 2. stochastic hill climbing - chooses a random state that is better
	 * 3. first choice - a kind of stochastic, where it generates successors randomly and uses the first successor that is better than the current state 
	 * only practical for states that have many many successors
	 * 4. random restart hill climbing - keep restarting algorithm with a random initial state until goal is found
	 */
	
	public void steepestAscentHillClimbingSearch() {
		
		
		
	}
	

	
	private void readFromFileToGrid(String fileName) throws IOException {
		InputStream inputStream = LocalSearch.class.getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		String firstLine = reader.readLine();
		String[] splitArray = firstLine.split("\\s+");
				
		int numFriends = Integer.parseInt(splitArray[0]);
		int numTrees = Integer.parseInt(splitArray[1]);

		int[][] treeLocations = new int[numTrees][2];
		for (int i = 0; i < numTrees; i++) {
			String nextTree = reader.readLine();
			splitArray = nextTree.split("\\s+");
			// the sample input is 1-indexed
			treeLocations[i][0] = Integer.parseInt(splitArray[0]) - 1;
			treeLocations[i][1] = Integer.parseInt(splitArray[1]) - 1;
		}
		this.grid = new Grid(numFriends, numTrees, treeLocations);

	}
	
	public void printGrid() {
		GridLocation[][] gridArray = grid.getGrid();
		int length = gridArray.length;
		for (int i = 0; i < length; i++) {
			System.out.println();
			for (int j = 0; j < length; j++) {
				if (gridArray[i][j].getState() == DomainState.FRIEND) {
					System.out.print("F");
				} else if (gridArray[i][j].getState() == DomainState.TREE) {
					System.out.print("T");
				} else {
					System.out.print(" ");
				}
			}
		}
	}
}