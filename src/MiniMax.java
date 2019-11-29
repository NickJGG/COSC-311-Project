import java.util.Random;

public class MiniMax extends DraughtsEngine {
	int captureScore, kingScore;
	
	public MiniMax(DraughtsTree tree, char player) {
		super(tree, "MiniMax", player);	
	}
	public MiniMax(DraughtsTree tree, String name, char player) {
		super(tree, name, player);
	}

	@Override
	public Move makeMove() {
		DraughtsNode node = tree.getRoot();
		
		int i = (new Random()).nextInt(node.getChildren().size()),
				bestScore = (int) Double.NEGATIVE_INFINITY;
		
		DraughtsNode bestNode = node.getChildren().get(i);
		
		for (DraughtsNode child : node.getChildren()) { // Find child with highest score
			int score = minimax(child, false);
			
			if (score > bestScore) {
				bestScore = score;
				bestNode = child;
			}
		}
		
		return bestNode.move;
	}
	
	int evaluateBoard(DraughtsNode node) {
		if (tree.whiteWin(node))
			return -100;
		
		if (tree.blackWin(node))
			return 100;
		
		Move move = node.move;
		
		int multiplier = move.getPlayer() == 'w' ? -1 : 1, 
			value = 0,
			depth = node.depth - tree.getRoot().depth;
		
		if (move.isCapture())
			value += 5 * multiplier;
		
		if (move.isKing())
			value += 4 * multiplier;
		
		System.out.print("(" + move.toString() + " = " + (value * depth) + " // cap: " + move.isCapture() + " // depth: " + node.depth + " ) ");
		
		return value * depth;
	}
	int minimax(DraughtsNode node, boolean maximizing) {
		int score = evaluateBoard(node);
		
		if (score == 100 || score == -100) // If a player has won
			return score;
		
		if (node.getChildren().size() == 0) // If no more moves left
			return score;
		
		int bestScore = 0;
		
		if (maximizing) { // Alternating min/max
			bestScore = -1000;
			
			for (DraughtsNode child : node.getChildren()) { // Get highest score of current node
				bestScore = Math.max(bestScore, minimax(child, false));
			}
		} else {
			bestScore = 1000;
			
			for (DraughtsNode child : node.getChildren()) { // Get lowest score of current node
				bestScore = Math.min(bestScore, minimax(child, true));
			}
		}
		
		//System.out.print("(" + node.move.toString() + " = " + (bestScore * depth) + " // cap: " + node.move.isCapture() + " ) ");
		
		return bestScore;
	}
}
