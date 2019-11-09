import java.util.ArrayList;

public class Draughts {
	int whites, blacks, kings;
	
	public Draughts() {
		whites = 0b00000000000000000000111111111111;
		blacks = 0b11111111111100000000000000000000;
		kings  = 0b00000000000000000000000000000000;
	}
	
	public boolean pieceExists(int position) {
		int digit = 1 << (32 - position);
		
		return (whites & digit) != 0 || (blacks & digit) != 0;
	}
	public boolean pieceExists(int board, int position) {
		int digit = 1 << (32 - position);
		
		return (board & digit) != 0;
	}
	public boolean inRange(int position) {
		return position >= 0 && position < 32;
	}
	public boolean isLegalMove(char player, int source, int destination) {
		if (!inRange(destination) || pieceExists(destination))
			return false;
		
		return getLegalMoves(player, source).contains(destination);
	}
	
	public void printBoard() {
		int row = 0;
		
		for (int i = 1; i <= 32; i++) {
			char c = ' ';
			
			if (pieceExists(whites, i))
				c = 'w';
			if (pieceExists(blacks, i))
				c = 'b';
			
			if (pieceExists(kings, i) && c != ' ')
				c++;
			
			if (row % 2 == 0)
				System.out.print("-" + c);
			else
				System.out.print(c + "-");
			
			if (i % 4 == 0) {
				System.out.println();
				row++;
			}
		}
	}
	public void move(char player, int source, int destination) {
		if (isLegalMove(player, source, destination))
			System.out.println(source + " to " + destination + " is legal");
		else
			System.out.println(source + " to " + destination + " is not legal");
	}
	
	public ArrayList<Integer> getLegalMoves(char player, int source) {
		ArrayList<Integer> moves = new ArrayList<>();
		
		int row = getRow(source),
				col = getColumn(source);
		
		if (player == 'b') {
			moves.add(source + 4); // Every piece has this spot as a legal move
			
			if (row % 2 == 0) {
				if (col > 1) 
					moves.add(source + 3);
			} else if (col < 4)
				moves.add(source + 5);
		} else {
			moves.add(source - 4); // Every piece has this spot as a legal move
			
			if (row % 2 == 0) {
				if (col > 1) 
					moves.add(source  - 5);
			} else if (col < 4)
				moves.add(source - 3);
		}
		
		return moves;
	}
	public ArrayList<ArrayList<Integer>> getAllLegalMoves() {
		ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
		
		for (int i = 1; i <= 32; i++) {
			moves.add(getLegalMoves(i > 16 ? 'w' : 'b', i));
		}
		
		return moves;
	}
	
	public int getRow(int position) {
		return ((position - 1) / 4) + 1;
	}
	public int getColumn(int position) {
		return (position - 1) % 4 + 1;
	}
	
	public int getWhites() {
		return whites;
	}
	public int getBlacks() {
		return blacks;
	}
	public int getKings() {
		return kings;
	}
}
