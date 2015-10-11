package main;

import java.io.IOException;

import searchAlgorithms.CandyLocation;
import searchAlgorithms.CandyLocation.Color;
import searchAlgorithms.Game;
import searchAlgorithms.GridLocation;
import searchAlgorithms.LocalSearch;
import searchAlgorithms.LocalSearch.Local_Search_Versions;
import searchAlgorithms.MiniMax;
import searchAlgorithms.Search;

public class Tester {

	public static final String defaultForestPath = "../forests/input.txt";
	
	public Tester() {
	
	}
	
	public void runAllLocalSearchTests() throws IllegalAccessException, IOException {
		
		printForest(defaultForestPath);
		runLocalSearchTest(Local_Search_Versions.SAHC, defaultForestPath, 100);
		runLocalSearchTest(Local_Search_Versions.SAHCV2, defaultForestPath, 100);
		runLocalSearchTest(Local_Search_Versions.SAHCV3, defaultForestPath, 100);
		runLocalSearchTest(Local_Search_Versions.SHC, defaultForestPath, 100);
	}
	
	private void printForest(String forestFilePath) throws IllegalAccessException, IOException {
		LocalSearch search = new LocalSearch(forestFilePath);
		System.out.println("Default Forest Unsolved");
		search.printGrid();
		System.out.println("-----------------------------------------------------------");
	}
	
	private void runLocalSearchTest(Local_Search_Versions version, String forestFilePath, int iterations) throws IllegalAccessException, IOException {
		int foundOptimalCount = 0;
		int sumStepsForOptimal = 0;
		int sumStepsForNonOptimal = 0;
		GridLocation[][] solutionGrid = null;
		for (int i = 0; i < iterations; i++) {
			LocalSearch localSearch = new LocalSearch(forestFilePath);
			int result = localSearch.localSearch(version);
			if (result == 0) {
				foundOptimalCount++;
				sumStepsForOptimal += localSearch.getTotalSteps();
				solutionGrid = localSearch.getGrid().getGrid();
			} else {
				sumStepsForNonOptimal += localSearch.getTotalSteps();
			}
		}
		printLocalSearchResults(version, iterations, foundOptimalCount, sumStepsForOptimal, sumStepsForNonOptimal, solutionGrid);
	}
	
	private void printLocalSearchResults(Local_Search_Versions version, int iterations, int foundOptimalCount, int sumStepsForOptimal, int sumStepsForNonOptimal, GridLocation[][] solution) {
		if (solution != null) {
			System.out.println("An optimal solution from using the " + version + " algorithm");
			LocalSearch.printGrid(solution);
			System.out.println();
		}
		System.out.println("Out of " + iterations + " runs, " + version + " found the optimal solution " + foundOptimalCount + " times.");
		System.out.println("Average Steps to an optimal solution " + (sumStepsForOptimal / foundOptimalCount) );
		System.out.println("Average Steps for non-optimal solution "  + (sumStepsForNonOptimal / (iterations - foundOptimalCount)));
		System.out.println("-----------------------------------------------------------");
	}

	private void printGame(CandyLocation[][] board, int rows, int columns){
		Color positionColor;
		String line = "";
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				positionColor = board[i][j].getColor();
				
				if (positionColor == Color.BLUE) {
					line += "B ";
				} else if (positionColor == Color.GREEN) {
					line += "G ";
				} else if (positionColor == Color.EMPTY) {
					line += "E ";
				}
			}
		
			System.out.println(line);
			line = "";
		}
		
	}
	
	private void runGame(String gameFilePath) throws IOException {
		Search player_1 = new MiniMax();
		Search player_2 = new MiniMax();
		int depth = 3;
		Game game = new Game(gameFilePath, player_1, player_2, depth);
		printGame(game.getBoard(), game.getRows(), game.getColumns());
		if (game.checkWinner() == Color.BLUE) {
			System.out.println("A WINNER IS BLUE!");
		} else if (game.checkWinner() == Color.GREEN) {
			System.out.println("DA GREEN MACHINE!");
		} else {
			System.out.println("YOU BOTH SUCK!");
		}
	}
	
	public static void main(String[] args) throws IOException, IllegalAccessException {
		Tester tester = new Tester();
//		tester.runAllLocalSearchTests();
		tester.runGame("../gameBoards/AlmondJoy.txt");
	}
}