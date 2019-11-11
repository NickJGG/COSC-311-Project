import java.util.ArrayList;

public class Draughts {
	
	
	int whites, blacks, kings;
	
	public Draughts() {
		whites = 0b00000000000000000000111111111111;
		blacks = 0b11111111111100000000000000000000;
		kings  = 0b00000000000000000000000000000000;
	}
	public Draughts(int whites, int blacks, int kings) {
		this.whites = whites;
		this.blacks = blacks;
		this.kings  = kings;
	}
	
	public boolean pieceExists(int position) {
		int digit = 1 << (32 - position);
		
		return (whites & digit) != 0 || (blacks & digit) != 0;
	}
	public boolean pieceExists(int board, int position) {
		int digit = 1 << (32 - position);
		
		return (digit & board) != 0;
	}
	public boolean inRange(int position) {
		return position >= 0 && position <= 32;
	}
	public boolean isLegalMove(int source, int destination) {
		if (!inRange(source) || !pieceExists(source) || !inRange(destination) || pieceExists(destination))
			return false;
		
		return getLegalMoves(source).contains(destination);
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
	
	public void move(int source, int destination) {
		char player = getPlayer(source);
		
		if (isLegalMove(source, destination)) {
			System.out.println(source + " to " + destination + " is legal");
			
			if (player == 'w') {
				whites &= ~(1 << (32 - source));
				whites |= (1 << (32 - destination));
				
				// Make king if in king row
				if(destination >= 1 && destination <= 4)
					kings |= (1 << (32 - destination));
				
				if (Math.abs(source - destination) > 4)
					blacks &= ~(1 << (32 - getPositionBetween(source, destination))); // Capture black piece				
			} else {
				blacks &= ~(1 << (32 - source));
				blacks |= (1 << (32 - destination));
				
				// Make king if in king row
				if(destination >= 29)
					kings |= (1 << (32 - destination));
				
				if (Math.abs(source - destination) > 5)
					whites &= ~(1 << (32 - getPositionBetween(source, destination))); // Capture white piece				
			}
			// move king flag if it exists on this piece
			if(isKing(source)) {
				kings &= ~(1 << (32 - source));
				kings |= (1 << (32 - destination));
			}
		} else
			System.out.println(source + " to " + destination + " is not legal");
	}
	
	public char getPlayer(int position) {
		if (((1 << 32 - position) & whites) != 0)
			return 'w';
		
		return 'b';
	}
	public char getPlayer(int source, int destination) {
		if (((1 << 32 - getPositionBetween(source, destination)) & whites) != 0)
			return 'w';
		
		return 'b';
	}
	// Returns true if that position is a King
	public boolean isKing(int position) {
		return (((1 << 32 - position) & kings) != 0);
	}
	
	public ArrayList<Integer> getLegalMoves(int source) {
		ArrayList<Integer> moves = new ArrayList<>();
		
		char player = getPlayer(source);
		
		int row = getRow(source),
				col = getColumn(source);
		
		if (player == 'b') {
			moves.add(source + 4); // Every piece has this spot as a legal move
			
			if (col < 4 && getPlayer(source, source + 9) == 'w')
				moves.add(source + 9);
			
			if (col > 1 && getPlayer(source, source + 7) == 'w')
				moves.add(source + 7);
			
			if (row % 2 == 0) {
				if (col > 1)
					moves.add(source + 3);
			} else if (col < 4)
				moves.add(source + 5);
			
			// If king, add king moves
			if(isKing(source)) {
				moves.add(source - 4); // Every piece has this spot as a legal move
				
				if (row % 2 == 0) {
					if (col > 1) 
						moves.add(source  - 5);
				} else if (col < 4)
					moves.add(source - 3);
			}
			
		} else {			
			moves.add(source - 4); // Every piece has this spot as a legal move
			
			if (col > 1 && getPlayer(source, source - 9) == 'b')
				moves.add(source - 9);
			
			if (col < 4 && getPlayer(source, source - 7) == 'b')
				moves.add(source - 7);
			
			if (row % 2 == 0) {
				if (col > 1)
					moves.add(source  - 5);
			} else if (col < 4)
					moves.add(source - 3);
			
			// If king, add king moves
			if(isKing(source)) {
				moves.add(source + 4); // Every piece has this spot as a legal move
				
				if (row % 2 == 0) {
					if (col > 1) 
						moves.add(source + 3);
				} else if (col < 4)
					moves.add(source + 5);
			}
			
		}
		
		return moves;
	}
	
	
	public ArrayList<ArrayList<Integer>> getAllLegalMoves() {
		ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
		
		for (int i = 1; i <= 32; i++) {
			moves.add(getLegalMoves(i));
		}
		
		return moves;
	}
	
	public int getRow(int position) {
		return ((position - 1) / 4) + 1;
	}
	public int getColumn(int position) {
		return (position - 1) % 4 + 1;
	}
	public int getPositionBetween(int source, int destination) {
		int min = Math.min(source, destination),
			max = Math.max(source, destination);
		
		return min + (max - min) / 2 + getRow(source) % 2;
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
