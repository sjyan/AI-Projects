package main;

import java.io.IOException;

import searchAlgorithms.AlphaBeta;
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
	
	private void runGame(String gameBoard, boolean playerA, boolean playerB) throws IOException {
		Search player_1;
		Search player_2;
		String title = "";
		
		if (playerA) {
			player_1 = new MiniMax();
			title += "Minimax vs. ";
		} else {
			player_1 = new AlphaBeta();
			title += "AlphaBeta vs. ";
		}
		
		if (playerB) {
			player_2 = new MiniMax();
			title += "Minimax";
		} else {
			player_2 = new AlphaBeta();
			title += "AlphaBeta";
		}
	
		int depth_1 = 3;
		int depth_2 = 3;
		
		String gameFilePath = "../gameBoards/" + gameBoard + ".txt";
		System.out.println(gameBoard);
		System.out.println(title);
		
		Game game = new Game(gameFilePath, player_1, player_2, depth_1, depth_2);
		printGame(game.getBoard(), game.getRows(), game.getColumns());
		if (game.checkWinner() == Color.BLUE) {
			System.out.println("Blue wins by: " + game.getScore());
			System.out.println("Blue score: " + game.getBlue() + " Green score: " + game.getGreen());
			System.out.println("Blue Average Move Time: " + game.getBlueTime() + "ms Green Average Move Time: " + game.getGreenTime() + "ms");
			System.out.println("Blue Total Nodes: " + game.getBlueNodes() + " Green Total Nodes: " + game.getGreenNodes());
			System.out.println("Blue Average Nodes: " + game.getBlueAvg() + " Green Average Nodes: " + game.getGreenAvg());
			System.out.println("--------------------------");
		} else if (game.checkWinner() == Color.GREEN) {
			System.out.println("Green Wins by: " + game.getScore());
			System.out.println("Blue score: " + game.getBlue() + " Green score: " + game.getGreen());
			System.out.println("Blue Average Move Time: " + game.getBlueTime() + "ms Green Average Move Time: " + game.getGreenTime() + "ms");
			System.out.println("Blue Total Nodes: " + game.getBlueNodes() + " Green Total Nodes: " + game.getGreenNodes());
			System.out.println("Blue Average Nodes: " + game.getBlueAvg() + " Green Average Nodes: " + game.getGreenAvg());
			System.out.println("--------------------------");
		} else {
			System.out.println("Tie Game");
			System.out.println("Blue score: " + game.getBlue() + " Green score: " + game.getGreen());
			System.out.println("Blue Average Move Time: " + game.getBlueTime() + "ms Green Average Move Time: " + game.getGreenTime() + "ms");
			System.out.println("Blue Total Nodes: " + game.getBlueNodes() + " Green Total Nodes: " + game.getGreenNodes());
			System.out.println("Blue Average Nodes: " + game.getBlueAvg() + " Green Average Nodes: " + game.getGreenAvg());
			System.out.println("--------------------------");
		}
	}
	
	private void runAllGames() throws IOException {
		runGame("AlmondJoy", true, true);
		runGame("AlmondJoy", false, false);
		runGame("AlmondJoy", false, true);
		runGame("AlmondJoy", true, false);
		
		runGame("Ayds", true, true);
		runGame("Ayds", false, false);
		runGame("Ayds", false, true);
		runGame("Ayds", true, false);
		
		runGame("Bit-O-Honey", true, true);
		runGame("Bit-O-Honey", false, false);
		runGame("Bit-O-Honey", false, true);
		runGame("Bit-O-Honey", true, false);
		
		runGame("Mounds", true, true);
		runGame("Mounds", false, false);
		runGame("Mounds", false, true);
		runGame("Mounds", true, false);
		
		runGame("ReesesPieces", true, true);
		runGame("ReesesPieces", false, false);
		runGame("ReesesPieces", false, true);
		runGame("ReesesPieces", true, false);
	}
	
	public static void main(String[] args) throws IOException, IllegalAccessException {
		Tester tester = new Tester();
		tester.runAllLocalSearchTests();
		tester.runAllGames();
	}
}