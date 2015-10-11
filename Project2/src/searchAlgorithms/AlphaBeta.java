package searchAlgorithms;

import searchAlgorithms.CandyLocation.Color;

public class AlphaBeta extends Search{

	private int alpha = Integer.MIN_VALUE;
	private int beta = Integer.MAX_VALUE;
	
	public AlphaBeta() {}

	@Override
	public Coordinate search(CandyLocation[][] board, int depth) {
		setColor(color);
		bestMove = new Coordinate(-1);
		
		if (depth == 0) {
			for (int i=0; i<rows; i++) {
				for (int j=0; j<columns; j++) {
					if (board[i][j].getColor() == Color.EMPTY) {
						nextMove = new Coordinate(i,j,checkUtil(color));
						if (bestMove.getUtility() < nextMove.getUtility()) {
							bestMove = nextMove;
						}
					}
				}
			}
		} else {
			for (int i=0; i<rows; i++) {
				for (int j=0; j<columns; j++) {
					if (board[i][j].getColor() != Color.EMPTY) {
						nextMove = new Coordinate(i,j,checkUtil(color));
						search(board, depth-1);
						if (bestMove.getUtility() < nextMove.getUtility()) {
							bestMove = nextMove;
						}
					}
				}
			}
		}
		
		return bestMove;
		
	}

}
