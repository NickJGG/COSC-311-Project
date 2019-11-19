
public class Main {
	public static void main(String[] args) {		
		Draughts game = new Draughts();
		game.play();

		GuiBoard b = new GuiBoard(game.getTree());
		b.update();
	}
}