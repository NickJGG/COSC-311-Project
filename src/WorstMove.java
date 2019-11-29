import java.util.Random;

public class WorstMove extends MiniMax {

	public WorstMove(DraughtsTree tree, char player) {
		super(tree, "Worst Move", player);
	}

	@Override
	public Move makeMove() {
		DraughtsNode node = tree.root;
		
		int i = (new Random()).nextInt(node.getChildren().size()),
				worstScore = (int) Double.POSITIVE_INFINITY;
		
		DraughtsNode worstNode = node.getChildren().get(i);
		
		for (DraughtsNode child : node.getChildren()) { // Find child with highest score
			int score = minimax(child, false);
			
			if (score < worstScore) {
				worstScore = score;
				worstNode = child;
			}
		}
		
		return worstNode.move;
	}
}
