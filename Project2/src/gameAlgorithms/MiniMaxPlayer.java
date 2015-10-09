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
		
		if(nextSetMoves.isEmpty() || depth == 0) { bestScore = evaluate(); }
		else {
			for(int[] move : nextSetMoves) {
				cells[move[0]][move[1]].setColor(color);
				if(color == myColor) {
					// myColor maximizing color
					currentScore = minimaxSearch(depth - 1, oppColor)[0];
					if(currentScore > bestScore) {
						bestScore = currentScore;
						bestRow = move[0];
						bestCol = move[1];
					} else {
						// oppColor minimizing color
						currentScore = minimaxSearch(depth - 1, myColor)[0];
						if(currentScore < bestScore) {
							bestScore = currentScore;
							bestRow = move[0];
							bestCol = move[1];
						}
					}
					
				}
				
				cells[move[0]][move[1]].setColor(Color.EMPTY);
			}
		}
		
		return new int[] {bestScore, bestRow, bestCol};
	}
	
	// Determine possible moves for next decision
	private List<int[]> generateMoves() {
		List<int[]> nextMoveSpace = new ArrayList<int[]>();
		
		if(hasWon(myColor) || hasWon(oppColor)) { return nextMoveSpace; }
		
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
	
	private boolean hasWon(Color color) {
		int myTotalValues = 0;
		int oppTotalValues = 0;
		
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				if(cells[row][col].getColor() == color) {
					myTotalValues += cells[row][col].getValue();
				} else if(cells[row][col].getColor() ==
						((color == Color.BLUE) ? Color.GREEN : Color.BLUE)) {
					oppTotalValues += cells[row][col].getValue();
				} else {
					// exists empty cells still
					return false;
				}
			}
		}
		
		return myTotalValues > oppTotalValues;
	}
	
}
