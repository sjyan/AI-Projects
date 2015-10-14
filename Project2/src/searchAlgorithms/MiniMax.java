package searchAlgorithms;

import searchAlgorithms.CandyLocation.Color;

public class MiniMax extends Search{

	public MiniMax() {}

	@Override
	public Coordinate search(CandyLocation[][] board, int depth, Color color, boolean max, int maxDepth) {
		//Makes sure depth is no greater than the remaining moves
		if (depth > maxDepth) {
			depth = maxDepth;
		}
		
		//Creates Best move and sets it to max or min depending on if node is max or min
		Coordinate bestMove;
		if (max) {
			bestMove = new Coordinate(MINIMUM);
		} else {
			bestMove = new Coordinate(MAXIMUM);
		}
		
		//Value of next nodes utility
		int nextUtil = 0;
		
		//Checks if min or max and if at leaf
		if (depth == 0 && max) {
			for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (board[i][j].getColor() == Color.EMPTY) {
					//Keeps track of total nodes
					nodes++;
					
					//Copy of board to manipulate
					CandyLocation[][] copyBoard = copyBoard(board);
					
					//Places move of interest onto board
					move(copyBoard, i, j, color);
					
					//Checks utility of said move
					nextUtil = checkUtil(copyBoard);
					
					//Sets best move to next move if better
					if (bestMove.getUtility() < nextUtil) {
						bestMove = new Coordinate(i, j, nextUtil);
					}
				}
			}
			}
		} else if (depth == 0 && !max) {
			for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (board[i][j].getColor() == Color.EMPTY) {
					nodes++;
					CandyLocation[][] copyBoard = copyBoard(board);
					move(copyBoard, i, j, color);
					nextUtil = checkUtil(copyBoard);
					if (bestMove.getUtility() > nextUtil) {
						bestMove = new Coordinate(i, j, nextUtil);
					}
				}
			}
			}
		} else {
			Coordinate nextMove;
			for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (board[i][j].getColor() == Color.EMPTY) {
					CandyLocation[][] copyBoard = copyBoard(board);
					move(copyBoard, i, j, color);
					
					//Recursive search for next move, includes swapping turns
					if (color == Color.BLUE) {
						nextMove = search(copyBoard, depth-1, Color.GREEN, !max, maxDepth);
						if (max) {
							if (bestMove.getUtility() < nextMove.getUtility()) {
								bestMove = new Coordinate(i, j, nextMove.getUtility());
							}
						} else {
							if (bestMove.getUtility() > nextMove.getUtility()) {
								bestMove = new Coordinate(i, j, nextMove.getUtility());
							}
						}
					} else {
						nextMove = search(copyBoard, depth-1, Color.BLUE, !max, maxDepth);
						if (max) {
							if (bestMove.getUtility() < nextMove.getUtility()) {
								bestMove = new Coordinate(i, j, nextMove.getUtility());
							}
						} else {
							if (bestMove.getUtility() > nextMove.getUtility()) {
								bestMove = new Coordinate(i, j, nextMove.getUtility());
							}
						}
					}
				}
			}
			}
		}
		
//		System.out.println("Best Move = (" + bestMove.getX() + ", " + bestMove.getY() + ") util = " + bestMove.getUtility());
		return bestMove;
	}

	@Override
	public boolean getSearch() {
		//True for MiniMax
		return true;
	}
	
}
