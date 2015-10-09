package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class Search {
	
	protected CandyLocation[][] grid = new CandyLocation[6][6];
	protected int[][] values = new int[6][6];
	
	public Search(CandyLocation[][] board, int[][] values) throws IOException {
		
	}
	
	public abstract Coordinate search();
}
