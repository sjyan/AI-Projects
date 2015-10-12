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
		// Measure candy parity, weighted by values
		// Measure candy stability, weighted by values
		int score = 0;
		int myCandies = 0, oppCandies = 0;
		int myStability = 0; int oppStability = 0;
		for(int row = 0; row < ROWS; row++) {
			for(int col = 0; col < COLS; col++) {
				if(cells[row][col].getColor() == myColor) {
					/*
					stability + value if stable
					stability - value if unstable
					stability + 0 if semi stable
					*/
					Cell[] neighbors = getNeighbors(cells[row][col]);
					boolean flankable = false;
					checkNeighbors:
					for(int i = 0; i < 4; i++) {
						Cell[] neighborsNeighbors = getNeighbors(neighbors[i]);
						for(int j = 0; j < 3; j++) {
							if(i + j != 3) {
								if(neighborsNeighbors[j].getColor()
										== oppColor) {
									break checkNeighbors;
								}
							}
						}
					}
					
					myStability += (flankable) ? -1 * cells[row][col].getValue()
							: cells[row][col].getValue();
					myCandies += cells[row][col].getValue();
				} else if(cells[row][col].getColor() == oppColor) {
					/*
					stability - value if stable
					stability + value if unstable
					stability + 0 if semi stable
					*/
					Cell[] neighbors = getNeighbors(cells[row][col]);
					boolean flankable = false;
					checkNeighbors:
					for(int i = 0; i < 4; i++) {
						Cell[] neighborsNeighbors = getNeighbors(neighbors[i]);
						for(int j = 0; j < 3; j++) {
							if(i + j != 3) {
								if(neighborsNeighbors[j].getColor()
										== oppColor) {
									break checkNeighbors;
								}
							}
						}
					}
					myStability += (flankable) ? cells[row][col].getValue()
							: -1 * cells[row][col].getValue();
					oppCandies += cells[row][col].getValue();
				} 
			}
		}
	
		double parity = (myCandies - oppCandies) / (myCandies + oppCandies);
		double stability = (myStability - oppStability) / 
				(myStability + oppStability);
		
		score += 100 * parity + 10 * stability;
		return score;
	}
	
	// helper function to determine a cell's neighbor cells
	private Cell[] getNeighbors(Cell cell) {
		Cell top =  (cell.getRow() > 0) 
				? cells[cell.getRow() - 1][cell.getCol()] : null;
		Cell left = (cell.getCol() > 0) 
				? cells[cell.getRow()][cell.getCol() - 1] : null;
		Cell right = (cell.getCol() < COLS - 1)
				? cells[cell.getRow()][cell.getCol() + 1] : null;
		Cell bottom = (cell.getRow() < ROWS - 1)
				? cells[cell.getRow() + 1][cell.getCol()] : null;
		
		return new Cell[] {top, left, right, bottom};
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
