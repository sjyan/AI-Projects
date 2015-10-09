package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import searchAlgorithms.GridLocation.DomainState;

public class MiniMax extends Search{

	CandyLocation[][] grid = new CandyLocation[6][6];
	int[][] values = new int[6][6];

	public MiniMax(CandyLocation[][] board, int[][] values) throws IOException {
		super(board, values);
	}

	public Coordinate search() {
		Coordinate move;
		int moveX = 0;
		int moveY = 0;
		
		
		for (int y=0; y<6; y++) {
			for (int x=0; x<6; x++) {
				
			}
		}
		
		move = new Coordinate(moveX,moveY);
		return move;
		
	}
}
