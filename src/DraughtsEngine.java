

public abstract class DraughtsEngine {
	// Name of this engine
	String name;
	
	// What color is this AI
	char player;
	
	public DraughtsEngine(String name, char player) {
		this.name = name;
		this.player = player;
	}
	
	public abstract Move makeMove(DraughtsTree tree);
	
}
