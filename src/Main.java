
public class Main {
	public static void main(String[] args) {
		//Draughts d = new Draughts();
		
		Draughts d = new Draughts(
				0b00000000000000000000000000000001,
				0b10000000000000000000000000000000,
				0b00000000000000000000000000000000);
		//d.printBoard();
		
		System.out.println(d.getAllLegalMoves());
		//System.out.println(d.getLegalMoves(32));
		
		DraughtsTree tree = new DraughtsTree(d);
		
		tree.populate();
		
		System.out.println(tree.root.children.size());
		System.out.println(tree.root.board.getAllLegalMoves());

		GuiBoard b = new GuiBoard(d);
		b.update();
	}
}