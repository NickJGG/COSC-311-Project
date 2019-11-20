
public class Move {
	private char player;
	private int source, dest;
	private boolean capture;
	
	public Move(char player, int source, int dest, boolean capture) {
		this.player = player;
		this.source = source;
		this.dest = dest;
		this.capture = capture;
	}

	public Move(char player, int source, int dest) {
		this.player = player;
		this.source = source;
		this.dest = dest;	
		this.capture = false;
		
		if (Math.abs(source - dest) > 4)
			capture = true;
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
	
	public String toString() {
		return player + ": " + source + ((capture)?" x ":" > ") + dest;
	}
	
	public boolean equals(Move m) {
		return (m.player == player && m.source == source && m.dest == dest && m.capture == capture);
	}
	
}
