package searchAlgorithms;

import searchAlgorithms.CandyLocation.Color;

public class AlphaBeta extends Search{
	
	public AlphaBeta() {}

	@Override
	public Coordinate search(CandyLocation[][] board, int depth, Color color, boolean max, int maxDepth) {
		Coordinate bestMove = search(board, depth, color, max, maxDepth, MINIMUM, MAXIMUM);
		return bestMove;
	}

	public Coordinate search(CandyLocation[][] board, int depth, Color color, boolean max, int maxDepth, int alpha, int beta) {
		int alfa = alpha;
		int betta = beta;
		
		if (depth > maxDepth) {
			depth = maxDepth;
		}
		
		Coordinate bestMove;
		if (max) {
			bestMove = new Coordinate(MINIMUM);
		} else {
			bestMove = new Coordinate(MAXIMUM);
		}
		int nextUtil = 0;
		
		if (depth == 0 && max) {
			outerloop:
			for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (board[i][j].getColor() == Color.EMPTY) {
					nodes++;
					CandyLocation[][] copyBoard = copyBoard(board);
					move(copyBoard, i, j, color);
					nextUtil = checkUtil(copyBoard);
					
					if (nextUtil >= betta) {
						bestMove = new Coordinate(i, j, MAXIMUM);
						break outerloop;
					}
					
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
			Coordinate nextMove;
			outerloop:
			for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (board[i][j].getColor() == Color.EMPTY) {
					CandyLocation[][] copyBoard = copyBoard(board);
					move(copyBoard, i, j, color);
					if (color == Color.BLUE) {
						nextMove = search(copyBoard, depth-1, Color.GREEN, !max, maxDepth, alfa, betta);
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
		return false;
	}
	
	
	
}
