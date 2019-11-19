import java.util.ArrayList;
import java.util.Stack;

public class DraughtsTree {
	int width = 3, height = 3, totalSpots = width * height * 2;
	
	DraughtsNode root;
	
	public DraughtsTree() {
		this.root = new DraughtsNode(this, 0b00000000000000000000111111111111, 0b11111111111100000000000000000000, 0b00000000000000000000000000000000, 0);
	}
	public DraughtsTree(DraughtsNode root) {
		this.root = root;
	}
	
	public boolean isComplete(DraughtsNode node) {
		return whiteWin(node) || blackWin(node) || getAllLegalMoves(node).size() == 0;
	}
	public boolean whiteWin(DraughtsNode node) {
		return node.blacks == 0;
	}
	public boolean blackWin(DraughtsNode node) {
		return node.whites == 0;
	}
	public boolean pieceExists(DraughtsNode node, int position) {
		int digit = 1 << (totalSpots - position);
		
		return (node.whites & digit) != 0 || (node.blacks & digit) != 0;
	}
	public boolean pieceExists(DraughtsNode node, int board, int position) {
		int digit = 1 << (totalSpots - position);
		
		return (digit & board) != 0;
	}
	public boolean inRange(int position) {
		return position >= 0 && position <= totalSpots;
	}
	public boolean isLegalMove(DraughtsNode node, int source, int destination) {
		if (!inRange(source) || !pieceExists(node, source) || !inRange(destination) || pieceExists(node, destination))
			return false;
		
		return getLegalMoves(node, source).contains(destination);
	}
	// Returns true if that position is a King
	public boolean isKing(DraughtsNode node, int position) {
		return (((1 << totalSpots - position) & node.kings) != 0);
	}
	
	public char getPlayer(DraughtsNode node, int position) {
		if (((1 << totalSpots - position) & node.whites) != 0)
			return 'w';
		
		return 'b';
	}
	public char getPlayer(DraughtsNode node, int source, int destination) {
		if (((1 << totalSpots - getPositionBetween(source, destination)) & node.whites) != 0)
			return 'w';
		
		if (((1 << totalSpots - getPositionBetween(source, destination)) & node.blacks) != 0)
			return 'b';
		
		return '-';
	}
	
	public ArrayList<Integer> getLegalMoves(DraughtsNode node, int source) {
		ArrayList<Integer> moves = new ArrayList<>();
		
		if (!inRange(source) || !pieceExists(node, source))
			return null;
		
		char player = getPlayer(node, source);
		
		int row = getRow(source),
			col = getColumn(source),
			base;
		
		if (player == 'b') {
			base = source + width;
			
			if (row < height - 1) {
				if (col < width && getPlayer(node, source, base + width + 1) == 'w')
					moves.add(base + width + 1);
				
				if (col > 1 && getPlayer(node, source, base + width - 1) == 'w')
					moves.add(base + width - 1);
			}
			
			if (row < height * 2) {
				moves.add(base);
				
				if (row % 2 == 0) {
					if (col > 1)
						moves.add(base - 1);
				} else if (col < width)
					moves.add(base + 1);
			}
			
			// If king, add king moves
			if(isKing(node, source)) {				
				base = source - width;
				
				if (row > 2) {
					if (col > 1 && getPlayer(node, source, base - width - 1) == 'b')
						moves.add(base - width - 1);
					
					if (col < 4 && getPlayer(node, source, base - width + 1) == 'b')
						moves.add(base - width + 1);
				}
				
				if (row > 1) {
					moves.add(base);
					
					if (row % 2 == 0) {
						if (col > 1)
							moves.add(base - 1);
					} else if (col < width)
							moves.add(base + 1);
				}
			}
			
		} else {
			base = source - width;
			
			if (row > 2) {
				if (col > 1 && getPlayer(node, source, base - width - 1) == 'b')
					moves.add(base - width - 1);
				
				if (col < 4 && getPlayer(node, source, base - width + 1) == 'b')
					moves.add(base - width + 1);
			}
			
			if (row > 1) {
				moves.add(base);
				
				if (row % 2 == 0) {
					if (col > 1)
						moves.add(base - 1);
				} else if (col < width)
						moves.add(base + 1);
			}
			
			// If king, add king moves
			if(isKing(node, source)) {				
				base = source + width;
				
				if (row < height - 1) {
					if (col < width && getPlayer(node, source, base + width + 1) == 'w')
						moves.add(base + width + 1);
					
					if (col > 1 && getPlayer(node, source, base + width - 1) == 'w')
						moves.add(base + width - 1);
				}
				
				if (row < height * 2) {
					moves.add(base);
					
					if (row % 2 == 0) {
						if (col > 1)
							moves.add(base - 1);
					} else if (col < width)
						moves.add(base + 1);
				}
			}	
		}
		
		return moves;
	}
	
	public ArrayList<ArrayList<Integer>> getAllLegalMoves(DraughtsNode node) {
		ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
		
		for (int i = 1; i <= totalSpots; i++) {
			ArrayList<Integer> legalMoves = getLegalMoves(node, i);
			
			if (legalMoves != null) {
				legalMoves.add(0, i);
				moves.add(legalMoves);
			}
		}
		
		return moves;
	}
	
	public int getRow(int position) {
		return ((position - 1) / width) + 1;
	}
	public int getColumn(int position) {
		return (position - 1) % height + 1;
	}
	public int getPositionBetween(int source, int destination) {
		int min = Math.min(source, destination),
			max = Math.max(source, destination);
		
		return min + (max - min) / 2 + getRow(source) % 2;
	}
	
	public void populate() {
		System.out.print("Populating Tree... ");
		
		Stack<DraughtsNode> nodes = new Stack<>(); // Using a stack + while loop reduces memory usage
		nodes.add(root);
		
		int count = 0, complete = 0;
		
		while(!nodes.isEmpty()) {
			DraughtsNode node = nodes.pop();
			
			if (isComplete(node))
				complete++;
			
			if (isComplete(node) || node.getMovesSinceCap() >= 4)				
				continue;
			
			for (int i = 1; i <= totalSpots; i++) {
				char player = getPlayer(node, i);
				
				if (pieceExists(node, i) && player != node.getLastPlayer()) { // If piece found is the opposite player
					ArrayList<Integer> moves = getLegalMoves(node, i);
					
					for (int move : moves) { // Add a new node for every possible move
						DraughtsNode newNode = new DraughtsNode(this, node);
						newNode.move(i, move);
						newNode.setLastPlayer(player);
						
						/*newNode.printBoard();
						System.out.println("Moves since last cap: " + newNode.getMovesSinceCap());
						System.out.println("Current player: " + player);
						System.out.println("Previous player: " + newNode.getLastPlayer());
						System.out.println(moves.toString() + "\n");*/
						
						nodes.push(newNode);
						
						node.addChild(newNode);
						
						count++;
					}
				}
			}
		}
		
		System.out.println("Done.");
		
		System.out.println(count);
		System.out.println(complete);
	}
	
	public DraughtsNode getRoot() {
		return root;
	}
	
	public void setRoot(DraughtsNode root) {
		this.root = root;
	}
}
