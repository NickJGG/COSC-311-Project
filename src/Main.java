import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {		
		Draughts game = new Draughts();
		
		
		game.play();
		
		GuiBoard b = new GuiBoard(game.getTree());
		b.update();
		
		for(Move m : game.tree.getAllLegalMoves(game.tree.root)) {
			System.out.println(m.toString());
		}
		
		//Move m = new Move('w', 18, 15, false);
		//System.out.println(m.toString());
		
		
//		for(ArrayList<Integer> legalMoves : game.getTree().getAllLegalMoves(game.getTree().root)) {
//			System.out.println(legalMoves);
//		}
	}
}