
public class Main {
	public static void main(String[] args) {
		Draughts d = new Draughts(
				0b00000000000001100000000000000000,
				0b00000000000000011000000000000000,
				0b00000000000001111000000000000000);
		d.printBoard();

		d.getAllLegalMoves();
		
		GuiBoard b = new GuiBoard(d);
		b.update();
		
		/*for (int i = 21; i <= 32; i++) {
			System.out.println(i + " | " + d.getLegalMoves('w', i));
		}*/
	}
}