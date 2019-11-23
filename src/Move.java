
public class Move {
	private char player;
	private int source, dest;
	private boolean capture, king;
	
	public Move(char player, int source, int dest, DraughtsTree tree) {
		this.player = player;
		this.source = source;
		this.dest = dest;
		this.capture = Math.abs(source - dest) > 4;
		this.king = !tree.isKing(tree.getRoot(), dest) && (player == 'w' ? dest <= tree.width : dest >= tree.totalSpots - tree.width);
	}
	
	public char getPlayer() {
		return player;
	}

	public int getSource() {
		return source;
	}
	public int getDest() {
		return dest;
	}

	public boolean isCapture() {
		return capture;
	}
	public boolean isKing() {
		return king;
	}
	
	public String toString() {
		return player + ": " + source + ((capture)?" x ":" > ") + dest;
	}
	
	public boolean equals(Move m) {
		return (m.player == player && m.source == source && m.dest == dest && m.capture == capture);
	}
	
}
