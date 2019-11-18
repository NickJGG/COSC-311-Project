
public class DraughtsTree {
	DraughtsNode root;
	
	public DraughtsTree() {
		this.root = new DraughtsNode(0b00000000000000000000111111111111, 0b11111111111100000000000000000000, 0b00000000000000000000000000000000, 0);
	}
	public DraughtsTree(DraughtsNode root) {
		this.root = root;
	}
	
	public void populate() {
		System.out.print("Populating Tree... ");
		root.populate();
		System.out.println("Done.");
	}
	
	public DraughtsNode getRoot() {
		return root;
	}
	
	public void setRoot(DraughtsNode root) {
		this.root = root;
	}
}
