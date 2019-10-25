import java.util.ArrayList;

public class TicTacTree {
	TicTacNode root;
	
	public TicTacTree() {
		root = new TicTacNode(0b000000000, 0b000000000);
		
		populate(root, false);
	}
	
	boolean isOccupied(int spot, TicTacNode node) {
		int bin = (int) Math.pow(2, spot);
		
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
		
		for (int i = 0; i < 9 - node.getLevel(); i++) {			
			for (int j = i + start; j < 9; j++) {
				if (!isOccupied(j, node)) {
					int player1 = node.getPlayer1(), player2 = node.getPlayer2();
					
					if (player)
						player2 += (int) Math.pow(2, j);
					else
						player1 += (int) Math.pow(2, j);
					
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