import java.util.ArrayList;

public class TicTacNode {
	private ArrayList<TicTacNode> children;
	private TicTacNode parent;

	private int player1, player2, level;

	public TicTacNode(TicTacNode parent, int player1, int player2) {
		this.parent = parent;
		this.children = new ArrayList<>();

		this.level = parent.getLevel() + 1;
		this.player1 = player1;
		this.player2 = player2;
	}
	public TicTacNode(int player1, int player2) {
		this.children = new ArrayList<>();

		this.level = 0;
		this.player1 = player1;
		this.player2 = player2;
	}

	public int getLevel() {
		return this.level;
	}
	public int getPlayer1() {
		return this.player1;
	}
	public int getPlayer2() {
		return this.player2;
	}

	public void addChild(TicTacNode node) {
		children.add(node);
	}
	public void print() {
		for (int i = 0; i < 9; i++) {
			int x = i % 3, y = i / 3, bin = (1 << (i));

			if ((player1 & bin) > 0)
				System.out.print("X");
			else if ((player2 & bin) > 0)
				System.out.print("O");
			else
				System.out.print("-");

			if (x == 2)
				System.out.print("");
		}
		System.out.print("\n");
	}

	//This method checks if the game has been completed on this given node.
	public boolean checkComplete() {
		if((player1 | player2) == 0b111111111)
			return true;

		boolean win = ((player1 & 0b111000000) == 0b111000000) ||
			((player1 & 0b000111000) == 0b000111000) ||
			((player1 & 0b000000111) == 0b000000111) ||
			((player1 & 0b100100100) == 0b100100100) ||
			((player1 & 0b010010010) == 0b010010010) ||
			((player1 & 0b001001001) == 0b001001001) ||
			((player1 & 0b100010001) == 0b100010001) ||
			((player1 & 0b001010100) == 0b001010100) ||

			((player2 & 0b111000000) == 0b111000000) ||
			((player2 & 0b000111000) == 0b000111000) ||
			((player2 & 0b000000111) == 0b000000111) ||
			((player2 & 0b100100100) == 0b100100100) ||
			((player2 & 0b010010010) == 0b010010010) ||
			((player2 & 0b001001001) == 0b001001001) ||
			((player2 & 0b100010001) == 0b100010001) ||
			((player2 & 0b001010100) == 0b001010100);
		return win;
	}

	public ArrayList<TicTacNode> getChildren(){
		return this.children;
	}
}
