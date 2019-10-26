import java.util.ArrayList;

public class TicTacTree {
	TicTacNode root;
	
	public TicTacTree() {
		root = new TicTacNode(0b000000000, 0b000000000);
		
		populate(root, false);
	}

	public TicTacTree(int xs, int os, boolean player) {
		root = new TicTacNode(xs, os);
		
		populate(root, player);
	}
	
	boolean isOccupied(int spot, TicTacNode node) {
		int bin = (1 << (spot));
		
		return (node.getPlayer1() & bin) > 0 || (node.getPlayer2() & bin) > 0;
	}
	
	int getRealSize(TicTacNode node) {
		int size = 1;
		
		for (TicTacNode child : node.getChildren()) {
			size += getRealSize(child);
		}
		
		return size;
	}
	
	void populate(TicTacNode node, boolean player) {
		int start = 0;

		//Now if a game is actually complete it won't make hypothetical boards that can't happen.
		if(node.checkComplete())
			return;
		
		for (int i = 0; i < 9 - node.getLevel(); i++) {
			for (int j = i + start; j < 9; j++) {
				if (!isOccupied(j, node)) {
					int player1 = node.getPlayer1(), player2 = node.getPlayer2();
					
					if (player)
						player2 += (1 << (j));
					else
						player1 += (1 << (j));
					
					TicTacNode child = new TicTacNode(node, player1, player2);					
					
					populate(child, !player);
					node.addChild(child);
					
					break;
				} else
					start++;
			}
		}
	}
	
	TicTacNode getRoot() {
		return this.root;
	}
	TicTacNode getBoardMove(int board) {
		for (TicTacNode node : root.getChildren()) {
			if (node.getPlayer1() == board || node.getPlayer2() == board)
				return node;
		}
		
		return null;
	}
	TicTacNode getBestMove(TicTacNode node) {
		TicTacNode bestNode = null;
		
		int bestScore = (int) Double.NEGATIVE_INFINITY;
		
		for (TicTacNode child : node.getChildren()) { // Find child with highest score
			int score = minimax(child, false);
			
			if (score > bestScore) {
				bestScore = score;
				bestNode = child;
			}
		}
		
		return bestNode;
	}
	
	int minimax(TicTacNode node, boolean maximizing) {
		int score = evaluateBoard(node);
		
		if (score == 10 || score == -10) // If a player has won
			return score;
		
		if (node.getChildren().size() == 0) // If no more moves left
			return 0;
		
		int bestScore = 0;
		
		if (maximizing) { // Alternating min/max
			bestScore = (int) Double.NEGATIVE_INFINITY; // Some out of reach number
			
			for (TicTacNode child : node.getChildren()) { // Get highest score of current node
				bestScore = Math.max(bestScore, minimax(child, false));
			}
			
			return bestScore;
		} else {
			bestScore = (int) Double.POSITIVE_INFINITY; // Some out of reach number
			
			for (TicTacNode child : node.getChildren()) { // Get lowest score of current node
				bestScore = Math.min(bestScore, minimax(child, false));
			}
			
			return bestScore;
		}
	}
	int evaluateBoard(TicTacNode node) {
		if (node.checkComplete()) {
			if (node.checkPlayer1Win()) // If player wins this board, return -10
				return -10;
			else // If AI wins, return 10
				return 10;
		}
		
		return 0;
	}
	
	void setRoot(TicTacNode node) {
		root = node;
	}
}