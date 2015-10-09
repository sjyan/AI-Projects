package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import searchAlgorithms.CandyLocation.Color;

public abstract class Search {
	
	protected CandyLocation[][] grid = new CandyLocation[6][6];
	protected int[][] values;
	
	public Search(int[][] values) throws IOException {
		this.values = values;
	}
	
	public abstract Coordinate search(Color color);
	
	public abstract void setBoard(CandyLocation[][] grid);
	
	public abstract void setValues(int[][] values);
}
