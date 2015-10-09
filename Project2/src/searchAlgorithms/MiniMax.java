package searchAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import searchAlgorithms.CandyLocation.Color;

public class MiniMax extends Search{

	public MiniMax(int[][] values) throws IOException {
		super(values);
	}

	@Override
	public Coordinate search(Color color) {
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

	private int checkUtil() {
		int utility = 0;
		
		for (int i=0; i<6; i++) {
			for (int j=0; j<6; j++) {
				if (grid[i][j].getColor() == Color.BLUE) {
					utility += values[i][j];
				} else if(grid[i][j].getColor() == Color.GREEN) {
					utility -= values[i][j];
				}
			}
		}
		
		return utility;
	}
	
	@Override
	public void setBoard(CandyLocation[][] grid) {
		this.grid = grid;
	}
	
	@Override
	public void setValues(int[][] values) {
		this.values = values;
	}
}
