
public class Main {
	public static void main(String[] args) {
		Draughts d = new Draughts();
		d.printBoard();

		d.getAllLegalMoves();
		
		GuiBoard b = new GuiBoard(d);
		b.update();
		
		d.move('w', 21, 20);
		
		/*
		d.move('b', 6, 10);
		d.move('b', 10, 15);
		d.move('b', 12, 17);
		d.move('b', 12, 16);
		
		d.move('w', 26, 22);
		d.move('w', 23, 18);
		d.move('w', 21, 18);
		d.move('w', 21, 17);
		*/
		
		/*for (int i = 21; i <= 32; i++) {
			System.out.println(i + " | " + d.getLegalMoves('w', i));
		}*/
	}
}