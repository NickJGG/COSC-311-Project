import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {		
		Draughts game = new Draughts();
		game.play();
		
		System.out.println(game.tree.getPositionBetween(22, 17));
		
		
		GuiBoard b = new GuiBoard(game.getTree());
		b.update();
	}
}