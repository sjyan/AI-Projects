package searchAlgorithms;

import searchAlgorithms.CandyLocation.Color;

public class AlphaBeta extends Search{
	
	public AlphaBeta() {}

	@Override
	public Coordinate search(CandyLocation[][] board, int depth, Color color, boolean max, int maxDepth) {
		
		//Was a quick implementation, didn't have time to make a robust one
		Coordinate bestMove = search(board, depth, color, max, maxDepth, MINIMUM, MAXIMUM);
		return bestMove;
	}

	public Coordinate search(CandyLocation[][] board, int depth, Color color, boolean max, int maxDepth, int alpha, int beta) {
		//Spaghetti code, sorry. Keeps track of alpha beta
		int alfa = alpha;
		int betta = beta;
		
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
			//Sets Break point for pruning
			outerloop:
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
					
					//Checks for pruning
					if (nextUtil >= betta) {
						bestMove = new Coordinate(i, j, MAXIMUM);
						break outerloop;
					}
					
					//Sets best move to next move if better
					if (bestMove.getUtility() < nextUtil) {
						bestMove = new Coordinate(i, j, nextUtil);
					}
				}
			}
			}
		} else if (depth == 0 && !max) {
			outerloop:
			for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (board[i][j].getColor() == Color.EMPTY) {
					nodes++;
					CandyLocation[][] copyBoard = copyBoard(board);
					move(copyBoard, i, j, color);
					nextUtil = checkUtil(copyBoard);
					
					if (alfa >= nextUtil) {
						bestMove = new Coordinate(i, j, MINIMUM);
						break outerloop;
					}
					
					if (bestMove.getUtility() > nextUtil) {
						bestMove = new Coordinate(i, j, nextUtil);
					}
				}
			}
			}
		} else {
			//Sets move for search result
			Coordinate nextMove;
			outerloop:
			for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (board[i][j].getColor() == Color.EMPTY) {
					CandyLocation[][] copyBoard = copyBoard(board);
					move(copyBoard, i, j, color);
					
					//Recursive search for next move, includes swapping turns
					if (color == Color.BLUE) {
						nextMove = search(copyBoard, depth-1, Color.GREEN, !max, maxDepth, alfa, betta);
						if (max) {
							if (bestMove.getUtility() < nextMove.getUtility()) {
								bestMove = new Coordinate(i, j, nextMove.getUtility());
								
								//Sets Alpha from children
								alfa = nextMove.getUtility();
							}
						} else {
							if (bestMove.getUtility() > nextMove.getUtility()) {
								bestMove = new Coordinate(i, j, nextMove.getUtility());
								
								//Sets Beta from children
								betta = nextMove.getUtility();
							}
						}
					} else {
						nextMove = search(copyBoard, depth-1, Color.BLUE, !max, maxDepth, alfa, betta);
						if (max) {
							if (bestMove.getUtility() < nextMove.getUtility()) {
								bestMove = new Coordinate(i, j, nextMove.getUtility());
								alfa = nextMove.getUtility();
							}
						} else {
							if (bestMove.getUtility() > nextMove.getUtility()) {
								bestMove = new Coordinate(i, j, nextMove.getUtility());
								betta = nextMove.getUtility();
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
		//False for AlphaBeta
		return false;
	}
	
	
	
}
