import java.util.ArrayList;

public class Draughts {
	int whiteScore, blackScore;
	
	DraughtsTree tree;
	
	public Draughts() {
		this.whiteScore = 0;
		this.blackScore = 0;
		this.tree = new DraughtsTree();
		
		DraughtsNode root = new DraughtsNode(tree, 
				 0b000000000000111111,
				 0b111111000000000000,
				 0b000000000000000000);
		
		this.tree.setRoot(root);
	}
	
	public void play() {
		tree.populate();
		
	}
	
	public DraughtsTree getTree() {
		return this.tree;
	}
}
