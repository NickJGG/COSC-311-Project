import java.util.ArrayList;

public class DraughtsNode {
	Draughts board;
	
	int ply = 0;
	
	ArrayList<DraughtsNode> children = new ArrayList<DraughtsNode>();
	
	public DraughtsNode(Draughts board) {
		this.board = board;
	}
	public DraughtsNode(Draughts board, int ply) {
		this.ply = ply;
		this.board = board;
	}
	
	public void populate() {
		if(ply < 5) {
			for(ArrayList<Integer> move : board.getAllLegalMoves()) {
				if(move.size() > 1) {
					for(int i = 1; i < move.size(); i++) {
						Draughts copy = new Draughts(board.whites, board.blacks, board.kings);
						children.add(new DraughtsNode(copy, ply + 1));
						copy.move(move.get(0), move.get(i));
					}
				}
			}
			for(DraughtsNode child : children) {
				child.populate();
			}
		}
	}
	
}
