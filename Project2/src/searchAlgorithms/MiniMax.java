package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import searchAlgorithms.GridLocation.DomainState;

public class MiniMax {

	CandyLocation[][] grid = new CandyLocation[6][6];
	int[][] values = new int[6][6];
	
	public MiniMax(String fileName) throws IOException{
		readFromFile(fileName);
	}
	
	private void readFromFile(String fileName) throws IOException {
		InputStream inputStream = LocalSearch.class.getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		int value;
		
		for (int ver=0; ver<6; ver++) {
			String line = reader.readLine();
			String[] splitArray = line.split("\\s+");
			for (int hor=0; hor<6; hor++) {
				value = Integer.parseInt(splitArray[hor]);
				values[ver][hor] = value;
			}
		}
	}

	
}
