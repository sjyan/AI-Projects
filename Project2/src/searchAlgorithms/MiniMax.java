package searchAlgorithms;

import searchAlgorithms.CandyLocation.Color;

public class MiniMax extends Search{

	public MiniMax() {}

	@Override
	public Coordinate search(CandyLocation[][] board, int depth) {
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
