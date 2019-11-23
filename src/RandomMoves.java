import java.util.ArrayList;
import java.util.Random;

public class RandomMoves extends DraughtsEngine {

	public RandomMoves(char player) {
		super("Random Moves", player);
	}

	@Override
	public Move makeMove(DraughtsTree tree) {
		
		ArrayList<Move> moves = tree.getAllLegalMovesPlayer(tree.root, player);
		
		Random r = new Random();
		
		return moves.get(r.nextInt(moves.size()));
	}
	
}
