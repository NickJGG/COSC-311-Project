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
				System.out.println("");
		}
		
		System.out.println();
	}
	
	public ArrayList<TicTacNode> getChildren(){
		return this.children;
	}
}