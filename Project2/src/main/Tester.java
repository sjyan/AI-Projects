package main;

import java.io.IOException;

import searchAlgorithms.LocalSearch;

public class Tester {

	public Tester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		int foundOptimalCount = 0;
		for (int i = 0; i < 100; i++) {
			LocalSearch localSearch = new LocalSearch();
			foundOptimalCount = localSearch.steepestAscentHillClimbingSearch() != 0 ? foundOptimalCount : foundOptimalCount + 1;
		}
		System.out.println("Out of 100 runs, steepestAscentHillClimbingSearch found the optimal solution " + foundOptimalCount + " times.");
		
		foundOptimalCount = 0;
		for (int i = 0; i < 100; i++) {
			LocalSearch localSearch = new LocalSearch();
			foundOptimalCount = localSearch.steepestAscentHillClimbingSearchV2() != 0 ? foundOptimalCount : foundOptimalCount + 1;

		}
		System.out.println("Out of 100 runs, steepestAscentHillClimbingSearchV2 found the optimal solution " + foundOptimalCount + " times.");
		
		foundOptimalCount = 0;
		for (int i = 0; i < 100; i++) {
			LocalSearch localSearch = new LocalSearch();
			foundOptimalCount = localSearch.steepestAscentHillClimbingSearchV3() != 0 ? foundOptimalCount : foundOptimalCount + 1;

		}
		System.out.println("Out of 100 runs, steepestAscentHillClimbingSearchV3 found the optimal solution " + foundOptimalCount + " times.");
	}

}
// fuck a bitch in a peacoat