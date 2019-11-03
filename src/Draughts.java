
public class Draughts {
	static int whites = 0b00000000000000000000111111111111;
	static int blacks = 0b11111111111100000000000000000000;
	static int kings  = 0b00000000000000000000000000000000;
	
	public static void main(String[] args) {
		printBoard();
		//getAllLegalMoves(blacks);
		//getAllLegalMoves(whites);
		for(int i = 1; i <= 32; i++) {
			System.out.println(i + ": " + getRow(i) + ", " + getColumn(i));
		}
	}
	
	public static boolean check(int board, int position) {
		int digit = 1<<(32-position);
		return ((board & digit) != 0);
	}
	
	public static void printBoard() {
		int row = 0;
		for(int i = 1; i <= 32; i++) {
			char c = ' ';
			
			if(check(whites, i))
				c = 'w';
			if(check(blacks, i))
				c = 'b';
			if(check(kings, i) && c != ' ')
				c++;
			
			if(row % 2 == 0)
				System.out.print("#" + c);
			else
				System.out.print(c + "#");
			
			if(i % 4 == 0) {
				System.out.println();
				row++;
			}
		}
	}
	public static void getLegalMoves(int board, int position) {
		int testposition;
		
		if(!check(board, position))
			return;
		
		testposition = position-4;
		if(!check(board, testposition) && testposition > 0)
			System.out.println(position + " > " + testposition);
		testposition = position-3;
		if(!check(board, testposition) && testposition > 0)
			System.out.println(position + " > " + testposition);
		testposition = position+4;
		if(!check(board, testposition) && testposition > 0)
			System.out.println(position + " > " + testposition);
		testposition = position+5;
		if(!check(board, testposition) && testposition > 0)
			System.out.println(position + " > " + testposition);
	}
	public static void getAllLegalMoves(int board) {
		for(int i = 1; i <= 32; i++) {
			getLegalMoves(board, i);
		}
	}
	public static int getRow(int position) {
		return ((position-1)/4)+1;
	}
	public static int getColumn(int position) {
		return (((position-1)%4)+1)*2-((getRow(position)-1)%2);
	}
}
