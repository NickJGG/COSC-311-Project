
public class Main {
	public static void main(String[] args) {		
		DraughtsNode root = new DraughtsNode(0b00000000000000000000000000000001,
											0b10000000000000000000000000000000,
											0b00000000000000000000000000000000);
		
		DraughtsTree tree = new DraughtsTree(root);
		tree.populate();
		
		System.out.println(tree.root.children.size());

		GuiBoard b = new GuiBoard(tree);
		b.update();
	}
}