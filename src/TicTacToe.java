import java.util.Scanner;

public class TicTacToe {
	TicTacTree tree;
	
	public TicTacToe() {
		tree = new TicTacTree();
	}
	
	public void play() {
		int spot = 0;
		
		Scanner input = new Scanner(System.in);
		
		while (true) {
			tree.getRoot().print();
			
			System.out.print("Enter move: ");
			
			spot = input.nextInt();
			
			if (!tree.isOccupied(spot, tree.getRoot())) { // If spot chosen is free, find the node that matches the move and set that as the root
				TicTacNode node = tree.getBoardMove(tree.getRoot().getPlayer1() + (1 << spot));
				
				tree.setRoot(tree.getBoardMove(tree.getRoot().getPlayer1() + (1 << spot)));

				if (tree.getRoot().checkComplete()) {
					tree.getRoot().print();
					System.out.println("Winner");
					
					break;
				} else
					tree.setRoot(tree.getBestMove(tree.getRoot()));
			}
		}
	}
}