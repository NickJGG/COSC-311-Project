
public class Main {
	public static void main(String[] args) {
		TicTacToe t = new TicTacToe();
		
		System.out.println(t.tree.getRealSize(t.tree.root));
		for(TicTacNode node : t.tree.root.getChildren()) {
			System.out.println(node.checkComplete());
			node.print();
			
		}
		
		TicTacTree testTree = new TicTacTree(0b001011000, 0b000100011, true);

		System.out.println(testTree.getRealSize(testTree.root));

		for(TicTacNode node : testTree.root.getChildren()) {
			System.out.println(node.checkComplete());
			node.print();
		}
	}
}