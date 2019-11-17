
public class DraughtsTree {
	DraughtsNode root;
	
	public DraughtsTree(Draughts board) {
		root = new DraughtsNode(board);
	}
	
	public void populate() {
		System.out.print("Populating Tree... ");
		root.populate();
		System.out.println("Done.");
	}
	
}
