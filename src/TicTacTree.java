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
					//child.print();
					
					
					populate(child, !player);
					node.addChild(child);
					
					
					break;
				} else
					start++;
			}
		}

	}


	
	void setRoot(TicTacNode node) {
		root = node;
	}
}