import java.util.Random;

public class MiniMax extends DraughtsEngine {

	public MiniMax(char player) {
		super("MiniMax", player);
		
	}

	@Override
	public Move makeMove(DraughtsTree tree) {
		DraughtsNode node = tree.root;
		
		int i = (new Random()).nextInt(node.getChildren().size()),
				bestScore = (int) Double.NEGATIVE_INFINITY;
		
		DraughtsNode bestNode = node.getChildren().get(i);
		
		for (DraughtsNode child : node.getChildren()) { // Find child with highest score
			int score = minimax(child, false, tree);
			
			if (score > bestScore) {
				bestScore = score;
				bestNode = child;
			}
		}
		
		return bestNode.move;
	}
	
	int minimax(DraughtsNode node, boolean maximizing, DraughtsTree tree) {
		int score = tree.evaluateBoard(node);
		
		if (score == 100 || score == -100) // If a player has won
			return score;
		
		if (node.getChildren().size() == 0) // If no more moves left
			return score;
		
		int bestScore = 0;
		
		if (maximizing) { // Alternating min/max
			bestScore = -1000;
			
			for (DraughtsNode child : node.getChildren()) { // Get highest score of current node
				bestScore = Math.max(bestScore, minimax(child, false, tree));
			}
		} else {
			bestScore = 1000;
			
			for (DraughtsNode child : node.getChildren()) { // Get lowest score of current node
				bestScore = Math.min(bestScore, minimax(child, true, tree));
			}
		}
		
		//System.out.print("(" + node.move.toString() + " = " + (bestScore * depth) + " // cap: " + node.move.isCapture() + " ) ");
		
		return bestScore;
	}
}
