public abstract class DraughtsEngine {
	// Name of this engine
	String name;
	
	// What color is this AI
	char player;
	
	DraughtsTree tree;
	
	public DraughtsEngine(DraughtsTree tree, String name, char player) {
		this.tree = tree;
		this.name = name;
		this.player = player;
	}
	
	public abstract Move makeMove();
}
