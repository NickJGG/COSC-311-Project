import java.util.ArrayList;

public class DraughtsNode {	
	int ply = 0, whites, blacks, kings, movesSinceCap = 0;
	
	//The move that was taken to get here;
	Move move;
	
	//The depth of this node in the tree
	int depth = 0;
	
	char lastPlayer;
	
	// 0 = Ongoing, 1 = white win, 2 = black win, 3 = tie;
	int winState = 0;
	
	DraughtsTree tree;
	
	ArrayList<DraughtsNode> children = new ArrayList<DraughtsNode>();
	
	public DraughtsNode(DraughtsTree tree) {
		this.tree = tree;
		this.whites = 0b00000000000000000000111111111111;
		this.blacks = 0b11111111111100000000000000000000;
		this.kings  = 0b00000000000000000000000000000000;
	}
	public DraughtsNode(DraughtsTree tree, DraughtsNode node) {
		this.tree = tree;
		this.whites = node.whites;
		this.blacks = node.blacks;
		this.kings = node.kings;
		this.movesSinceCap = node.getMovesSinceCap();
	}
	public DraughtsNode(DraughtsTree tree, DraughtsNode node, Move move) {
		this.tree = tree;
		this.whites = node.whites;
		this.blacks = node.blacks;
		this.kings = node.kings;
		this.movesSinceCap = node.getMovesSinceCap();
		this.depth = node.depth + 1;
		this.move = move;
		this.move(move);
	}
	public DraughtsNode(DraughtsTree tree, DraughtsNode node, int ply) {
		this.tree = tree;
		this.whites = node.whites;
		this.blacks = node.blacks;
		this.kings = node.kings;
		this.ply = ply;
	}
	public DraughtsNode(DraughtsTree tree, int whites, int blacks, int kings) {
		this.tree = tree;
		this.whites = whites;
		this.blacks = blacks;
		this.kings  = kings;
	}
	public DraughtsNode(DraughtsTree tree, int whites, int blacks, int kings, int ply) {
		this.tree = tree;
		this.whites = whites;
		this.blacks = blacks;
		this.kings  = kings;
		this.ply = ply;
	}	
	
	public void printBoard() {
		int row = 0;
		
		for (int i = 1; i <= tree.totalSpots; i++) {
			char c = ' ';
			
			if (tree.pieceExists(this, whites, i))
				c = 'w';
			if (tree.pieceExists(this, blacks, i))
				c = 'b';
			
			if (tree.pieceExists(this, kings, i) && c != ' ')
				c -= 32;
			
			if (row % 2 == 0)
				System.out.print("-" + c);
			else
				System.out.print(c + "-");
			
			if (i % tree.width == 0) {
				System.out.println();
				row++;
			}
		}
	}
	
	public void move(Move m) {
		int source = m.getSource(),
			destination = m.getDest();
		
		char player = m.getPlayer();
		
		if (tree.isLegalMove(this, m)) {
			movesSinceCap++;
			
			if (player == 'w') {
				whites &= ~(1 << (tree.totalSpots - source));
				whites |= (1 << (tree.totalSpots - destination));
				
				// Make king if in king row
				if(destination >= 1 && destination <= tree.width)
					kings |= (1 << (tree.totalSpots - destination));
				
				if (Math.abs(source - destination) > tree.width + 1) {
					blacks &= ~(1 << (tree.totalSpots - tree.getPositionBetween(source, destination))); // Capture black piece
					
					movesSinceCap = 0;
				}
			} else {
				blacks &= ~(1 << (tree.totalSpots - source));
				blacks |= (1 << (tree.totalSpots - destination));
				
				// Make king if in king row
				if(destination > tree.totalSpots - tree.width)
					kings |= (1 << (tree.totalSpots - destination));
				
				if (Math.abs(source - destination) > tree.width + 1) {
					whites &= ~(1 << (tree.totalSpots - tree.getPositionBetween(source, destination))); // Capture white piece
					
					movesSinceCap = 0;
				}
			}
			// move king flag if it exists on this piece
			if(tree.isKing(this, source)) {
				kings &= ~(1 << (tree.totalSpots - source));
				kings |= (1 << (tree.totalSpots - destination));
			}
		}
	}
	
	// Recursive populate
	public void populate() {
		if (tree.isComplete(this)) {
			System.out.println("*");
			
			return;
		}
		if (getMovesSinceCap() >= 5) {
			System.out.println("x");
			
			return;
		}
		if (depth > tree.depth) {
			System.out.println("!");
			return;
		}
		
		char newPlayer = lastPlayer == 'w' ? 'b' : 'w';
		
		for (Move move : tree.getAllLegalMovesPlayer(this, newPlayer)) {
			DraughtsNode newNode = new DraughtsNode(this.tree, this, move);
			//newNode.move(move);
			newNode.setLastPlayer(move.getPlayer());
			this.addChild(newNode);
			newNode.populate();
		}
		System.out.println(".");
		
	}
	
	public int getWhites() {
		return whites;
	}
	public int getBlacks() {
		return blacks;
	}
	public int getKings() {
		return kings;
	}
	public int getMovesSinceCap() {
		return this.movesSinceCap;
	}

	public char getLastPlayer() {
		return this.lastPlayer;
	}
	
	public ArrayList<DraughtsNode> getChildren(){
		return this.children;
	}
	
	public void setLastPlayer(char lastPlayer) {
		this.lastPlayer = lastPlayer;
	}
	public void addChild(DraughtsNode node) {
		this.children.add(node);
	}
}
