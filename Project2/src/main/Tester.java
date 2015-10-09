package main;

import java.io.IOException;

import searchAlgorithms.GridLocation;
import searchAlgorithms.LocalSearch;
import searchAlgorithms.LocalSearch.Local_Search_Versions;

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

	public static void main(String[] args) throws IOException, IllegalAccessException {
		Tester tester = new Tester();
		tester.runAllLocalSearchTests();
	}
}