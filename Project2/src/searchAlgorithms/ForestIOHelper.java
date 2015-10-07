package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ForestIOHelper {
	
	protected static int friends, trees;
	
	public static int[][] generate2DArrayBoardFromInput(String fileName) throws Exception {
		InputStream inputStream = ForestIOHelper.class.getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		int[][] board;
		
		String[] coordinate;
		String line = "";
		
		//Get number of kids and trees
		if ((line = reader.readLine()) != null) {
			coordinate = line.split("\\s+");
			if (coordinate.length != 2) {
				throw new IOException();
			}
			friends = Integer.parseInt(coordinate[0]);
			trees = Integer.parseInt(coordinate[1]);
			
			board = new int[friends][friends];
		} else {
			throw new IOException();
		}
		
		int row, col;
		//populate board with trees
		for (int i=0; i<trees; i++) {
			line = reader.readLine();
			
			coordinate = line.split("\\s+");
			row = Integer.parseInt(coordinate[0]);
			col = Integer.parseInt(coordinate[1]);
			
			board[row][col] += 1;
		}

		
		return board;
		
	}
}
