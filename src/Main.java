import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {		
		Draughts game = new Draughts();
		
		
		game.play();
		
		GuiBoard b = new GuiBoard(game.getTree());
		b.update();
		
		
//		for(ArrayList<Integer> legalMoves : game.getTree().getAllLegalMoves(game.getTree().root)) {
//			System.out.println(legalMoves);
//		}
	}
}