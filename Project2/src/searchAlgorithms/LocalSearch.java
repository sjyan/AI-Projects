package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import searchAlgorithms.GridLocation.DomainState;

public class LocalSearch {

	private static final int MAX_SIDEWAYS = 10;
	private static final int OPTIMAL_SOLUTION = 0;

	Grid grid;
	
	public LocalSearch() throws IOException {
		readFromFileToGrid("../forests/input.txt");
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
	
	public int steepestAscentHillClimbingSearch() {
		int currentColumn = 0;
		int minimaCounter = 0;
		while (true) {
			int pivotColumn = currentColumn % grid.getNumFriends();
			minimaCounter = findNextBestLocationForFriend(pivotColumn) == 0 ? 0 : (minimaCounter + 1);
			if (calculateHeuristic(grid) == OPTIMAL_SOLUTION) {
				break;
			}
			// break when no friends have moved in any column
			if (minimaCounter == grid.getNumFriends()) {
				break;
			}
			currentColumn++;
		}
		//System.out.println("The ending heuristic is " + calculateHeuristic(grid));
		//printGrid();
		return calculateHeuristic(grid);
	}
	
	// returns 1 if friend changed to a better location, 0 otherwise
	private int findNextBestLocationForFriend(int column) {
		GridLocation[][] gridArray = grid.getGrid();
		int currentIndex = grid.getColumnToFriendMap().get(column);
		
		int nextBestIndex = currentIndex;
		int bestHeuristic = calculateHeuristic(grid);
		
		for (int i = 0; i < grid.getNumFriends(); i++) {
			if (i != currentIndex) {
				if (gridArray[i][column].getState() == DomainState.EMPTY) {
					gridArray[nextBestIndex][column].setState(DomainState.EMPTY);
					gridArray[i][column].setState(DomainState.FRIEND);
					int heuristic = calculateHeuristic(grid);
					if (heuristic < bestHeuristic) {
						nextBestIndex = i;
						bestHeuristic = heuristic;
					} else {
						gridArray[i][column].setState(DomainState.EMPTY);
					}
				}
			}
		}
		gridArray[nextBestIndex][column].setState(DomainState.FRIEND);
		grid.updateColumnToFriendMap(column, nextBestIndex);
		return nextBestIndex != currentIndex ? 0 : 1;
	}
	
	// allows sideways
	public int steepestAscentHillClimbingSearchV2() {
		int currentColumn = 0;
		int minimaCounter = 0;
		int sidewaysMoves = 0;
		while (true) {
			int pivotColumn = currentColumn % grid.getNumFriends();
			
			int result = findNextBestLocationForFriendV2(pivotColumn);
			if (calculateHeuristic(grid) == OPTIMAL_SOLUTION) {
				break;
			}
			
			if (result == 2) {
				sidewaysMoves++;
				minimaCounter = 0;
			} else if (result == 1) {
				minimaCounter++;
			} else {
				minimaCounter = 0;
				sidewaysMoves = 0;
			}

			// break when no friends have moved in any column
			if (minimaCounter == grid.getNumFriends()) {
				break;
			}
			if (sidewaysMoves == MAX_SIDEWAYS) {
				break;
			}
			
			currentColumn++;
		}
		//System.out.println("The ending heuristic is " + calculateHeuristic(grid));
		//printGrid();
		return calculateHeuristic(grid);
	}
	
	private int findNextBestLocationForFriendV2(int column) {
		GridLocation[][] gridArray = grid.getGrid();
		int currentIndex = grid.getColumnToFriendMap().get(column);
		
		int nextBestIndex = currentIndex;
		int bestHeuristic = calculateHeuristic(grid);
		int initialHeuristic = bestHeuristic;
		
		for (int i = 0; i < grid.getNumFriends(); i++) {
			if (i != currentIndex) {
				if (gridArray[i][column].getState() == DomainState.EMPTY) {
					gridArray[nextBestIndex][column].setState(DomainState.EMPTY);
					gridArray[i][column].setState(DomainState.FRIEND);
					int heuristic = calculateHeuristic(grid);
					// allow for sideways movement
					if (heuristic <= bestHeuristic) {
						nextBestIndex = i;
						bestHeuristic = heuristic;
					} else {
						gridArray[i][column].setState(DomainState.EMPTY);
					}
				}
			}
		}
		gridArray[nextBestIndex][column].setState(DomainState.FRIEND);
		grid.updateColumnToFriendMap(column, nextBestIndex);
		if (nextBestIndex != currentIndex) {
			// if same heuristic but friend moved, return 2, otherwise return 1
			return initialHeuristic != bestHeuristic ? 0 : 2;
		}
		// friend didn't move
		return 1; 
	}

	public int steepestAscentHillClimbingSearchV3() {
		int minimaCounter = 0;
		while (true) {
			int randomPivotColumn = (int) (Math.random() * grid.getNumFriends());
			minimaCounter = findNextBestLocationForFriend(randomPivotColumn) == 0 ? 0 : (minimaCounter + 1);
			if (calculateHeuristic(grid) == OPTIMAL_SOLUTION) {
				break;
			}
			// break when no friends have moved in any column
			if (minimaCounter == grid.getNumFriends()) {
				break;
			}
		}
		//System.out.println("The ending heuristic is " + calculateHeuristic(grid));
		//printGrid();
		return calculateHeuristic(grid);
	}
	
	public int stochasticHillClimbing() {
		
		
		return calculateHeuristic(grid);
	}
	private int calculateHeuristic(Grid grid) {
		int heuristic = 0;
		for (int columnIndex = 0; columnIndex < grid.getNumFriends(); columnIndex++) {
			int friendIndex = grid.getColumnToFriendMap().get(columnIndex);
			heuristic += findConflicts(friendIndex, columnIndex, grid);
		}
		return heuristic;
	}
	
	private int findConflicts(int x, int y, Grid grid) {
		int conflicts = 0;
		conflicts += findConflictUpRight(x, y, grid);
		conflicts += findConflictUpLeft(x, y, grid);
		conflicts += findConflictDownLeft(x, y, grid);
		conflicts += findConflictDownRight(x, y, grid);
		conflicts += findConflictsRight(x, y, grid);
		conflicts += findConflictsLeft(x, y, grid);
		return conflicts;
	}
	
	private int findConflictsRight(int x, int y, Grid grid) {
		y++;
		boolean conflict = false;
		while (y < grid.getNumFriends()) {
			DomainState state = grid.getGrid()[x][y].getState();
			if (state == DomainState.TREE) {
				break; 
			}
			if (state == DomainState.FRIEND) {
				conflict = true;
				break;
			}
			y++;
		}
		return conflict ? 1 : 0;
	}
	
	private int findConflictsLeft(int x, int y, Grid grid) {
		y--;
		boolean conflict = false;
		while (y >= 0) {
			DomainState state = grid.getGrid()[x][y].getState();
			if (state == DomainState.TREE) {
				break; 
			}
			if (state == DomainState.FRIEND) {
				conflict = true;
				break;
			}
			y--;
		}
		return conflict ? 1 : 0;
	}
	
	private int findConflictUpRight(int x, int y, Grid grid) {
		x--; y++;
		boolean conflict = false;
		while (x >= 0 && y < grid.getNumFriends()) {
			DomainState state = grid.getGrid()[x][y].getState();
			if (state == DomainState.TREE) {
				break; 
			}
			if (state == DomainState.FRIEND) {
				conflict = true;
				break;
			}
			x--; y++;
		}
		return conflict ? 1 : 0;
	}
	
	private int findConflictUpLeft(int x, int y, Grid grid) {
		x--; y--;
		boolean conflict = false;
		while (x >= 0 && y >= 0) {
			DomainState state = grid.getGrid()[x][y].getState();
			if (state == DomainState.TREE) {
				break; 
			}
			if (state == DomainState.FRIEND) {
				conflict = true;
				break;
			}
			x--; y--;
		}
		return conflict ? 1 : 0;
	}

	private int findConflictDownLeft(int x, int y, Grid grid) {
		x++; y--;
		boolean conflict = false;
		while (x < grid.getNumFriends() && y >= 0) {
			DomainState state = grid.getGrid()[x][y].getState();
			if (state == DomainState.TREE) {
				break; 
			}
			if (state == DomainState.FRIEND) {
				conflict = true;
				break;
			}
			x++; y--;
		}
		return conflict ? 1 : 0;
	}

	private int findConflictDownRight(int x, int y, Grid grid) {
		x++; y++;
		boolean conflict = false;
		while (x < grid.getNumFriends() && y < grid.getNumFriends()) {
			DomainState state = grid.getGrid()[x][y].getState();
			if (state == DomainState.TREE) {
				break; 
			}
			if (state == DomainState.FRIEND) {
				conflict = true;
				break;
			}
			x++; y++;
		}
		return conflict ? 1 : 0;
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