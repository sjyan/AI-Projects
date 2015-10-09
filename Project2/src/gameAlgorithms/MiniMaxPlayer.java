package gameAlgorithms;
import java.util.ArrayList;
import java.util.List;

public class MiniMaxPlayer extends Player {
	private int maxDepth = 3;
	
	public MiniMaxPlayer(Cell[][] board) {
		super(board);
	}

	@Override
	int[] move() {
		int[] score = minimaxSearch(maxDepth, myColor);
		return new int[] {score[1], score[2]};
	}
	
	// search implementation
	private int[] minimaxSearch(int depth, Color color) {
		List<int[]> nextSetMoves = generateMoves();
		int bestScore = (color == myColor) ? 1 : 99; // set absolute min/max
		int bestRow = -1;
		int bestCol = -1;
		int currentScore;
		
		if(nextSetMoves.isEmpty()) { bestScore = evaluate(); }
		else {
			for(int[] move : nextSetMoves) {
				cells[move[0]][move[1]].setColor(myColor);
			}
		}
		
		return new int[] {bestScore, bestRow, bestCol};
	}
	
	// Determine possible moves for next decision
	private List<int[]> generateMoves() {
		List<int[]> nextMoveSpace = new ArrayList<int[]>();
		// Check if won
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				if(cells[row][col].getColor() == Color.EMPTY) {
					nextMoveSpace.add(new int[] {row, col});
				}
			}
		}
		
		return nextMoveSpace;
	}
	
	// heuristic function to determine next move
	private int evaluate() {
		// fix this first
		int score = 0;
		// After determining heuristic...
		return score;
	}
	
}
