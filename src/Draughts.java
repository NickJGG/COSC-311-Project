import java.util.ArrayList;

public class Draughts {
	int whiteScore, blackScore;
	
	DraughtsTree tree;
	
	public Draughts() {
		this.whiteScore = 0;
		this.blackScore = 0;
		this.tree = new DraughtsTree();
		
		DraughtsNode root = new DraughtsNode(tree, 
				 0b000000000000000011,
				 0b100000000000000000,
				 0b000000000000000000);
		
		this.tree.setRoot(root);
	}
	
	public void play() {
		long startTime = System.currentTimeMillis(), endTime, totalTime;
		
		tree.populate();
		
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		
		System.out.println("Done in: " + totalTime + "ms");
	}
	
	public DraughtsTree getTree() {
		return this.tree;
	}
}
