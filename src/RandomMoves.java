import java.util.ArrayList;
import java.util.Random;

public class RandomMoves extends DraughtsEngine {

	public RandomMoves(DraughtsTree tree, char player) {
		super(tree, "Random Moves", player);
	}

	@Override
	public Move makeMove() {
		ArrayList<Move> moves = tree.getAllLegalMovesPlayer(tree.getRoot(), player);
		
		Random r = new Random();
		
		return moves.get(r.nextInt(moves.size()));
	}
}
