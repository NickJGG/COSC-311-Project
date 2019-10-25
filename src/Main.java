
public class Main {
	public static void main(String[] args) {
		TicTacToe t = new TicTacToe();
		
		System.out.println(t.tree.getRealSize(t.tree.root));
		
		TicTacNode a = new TicTacNode(0b100000000, 0b000000001);
		a.print();
		
	}
}