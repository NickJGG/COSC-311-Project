import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import tictactoe.TicTacNode;

enum GameMode {
	EASY,
	MEDIUM,
	HARD,
	SMARTER
}

public class DraughtsTree {
	int width = 3, height = 3, totalSpots = width * height * 2,
		depth = 5, turnsToQuit = 10,
		whiteScore = 0, blackScore = 0;

	DraughtsNode root;
	
	DraughtsEngine engine;
	
	GameMode mode;

	public DraughtsTree() {
		this.root = new DraughtsNode(this, 0b00000000000000000000111111111111, 0b11111111111100000000000000000000,
				0b00000000000000000000000000000000, 0);
	
		setMode(GameMode.SMARTER);
	}
	public DraughtsTree(DraughtsNode root, GameMode mode) {
		this.root = root;

		setMode(mode);
	}
	
	void setMode(GameMode mode) {
		this.mode = mode;
		
		switch(mode) {
			case EASY:
				this.engine = new RandomMoves(this, 'b');
				
				break;
			case MEDIUM:
			case HARD:
				this.engine = new MiniMax(this, 'b');
				this.depth = mode.ordinal() * 3;
				
				break;
			case SMARTER:
				this.engine = new MiniMax(this, 'b');
				this.depth = 10;
				
				break;
		}
	}

	public boolean isComplete(DraughtsNode node) {
		return whiteWin(node) || blackWin(node) || getAllLegalMoves(node).size() == 0;
	}
	public boolean whiteWin(DraughtsNode node) {
		return node.blacks == 0 || getAllLegalMovesPlayer(node, 'b').size() == 0;
	}
	public boolean blackWin(DraughtsNode node) {
		return node.whites == 0 || getAllLegalMovesPlayer(node, 'w').size() == 0;
	}
	public boolean pieceExists(DraughtsNode node, int position) {
		int digit = 1 << (totalSpots - position);

		return (node.whites & digit) != 0 || (node.blacks & digit) != 0;
	}
	public boolean pieceExists(DraughtsNode node, int board, int position) {
		int digit = 1 << (totalSpots - position);

		return (digit & board) != 0;
	}
	public boolean inRange(int position) {
		return position >= 0 && position <= totalSpots;
	}
	public boolean isLegalMove(DraughtsNode node, Move m) {
		int source = m.getSource(),
			destination = m.getDest();

		if (!inRange(source) || !pieceExists(node, source) || !inRange(destination) || pieceExists(node, destination))
			return false;

		for (Move n : getLegalMoves(node, source)) {
			if (m.equals(n)) {
				return true;
			}
		}

		return false;
	}
	// Returns true if that position is a King
	public boolean isKing(DraughtsNode node, int position) {
		return (((1 << totalSpots - position) & node.kings) != 0);
	}

	public char getPlayer(DraughtsNode node, int position) {
		if (((1 << totalSpots - position) & node.whites) != 0)
			return 'w';

		return 'b';
	}
	public char getPlayer(DraughtsNode node, int source, int destination) {
		if (((1 << totalSpots - getPositionBetween(source, destination)) & node.whites) != 0)
			return 'w';

		if (((1 << totalSpots - getPositionBetween(source, destination)) & node.blacks) != 0)
			return 'b';

		return '-';
	}

	public ArrayList<Move> getLegalMoves(DraughtsNode node, int source) {
		ArrayList<Move> moves = new ArrayList<>();

		if (!inRange(source) || !pieceExists(node, source))
			return moves;

		char player = getPlayer(node, source);

		int row = getRow(source), col = getColumn(source), base;

		if (player == 'b') {
			base = source + width;

			if (row < height * 2 - 1) {
				if (col < width && getPlayer(node, source, base + width + 1) == 'w')
					moves.add(new Move(player, source, base + width + 1, this));

				if (col > 1 && getPlayer(node, source, base + width - 1) == 'w')
					moves.add(new Move(player, source, base + width - 1, this));
			}

			if (row < height * 2) {
				moves.add(new Move(player, source, base, this));

				if (row % 2 == 0) {
					if (col > 1)
						moves.add(new Move(player, source, base - 1, this));
				} else if (col < width)
					moves.add(new Move(player, source, base + 1, this));
			}

			// If king, add king moves
			if (isKing(node, source)) {
				base = source - width;

				if (row > 2) {
					if (col > 1 && getPlayer(node, source, base - width - 1) == 'w')
						moves.add(new Move(player, source, base - width - 1, this));

					if (col < width && getPlayer(node, source, base - width + 1) == 'w')
						moves.add(new Move(player, source, base - width + 1, this));
				}

				if (row > 1) {
					moves.add(new Move(player, source, base, this));

					if (row % 2 == 0) {
						if (col > 1)
							moves.add(new Move(player, source, base - 1, this));
					} else if (col < width)
						moves.add(new Move(player, source, base + 1, this));
				}
			}

		} else {
			base = source - width;

			if (row > 2) {
				if (col > 1 && getPlayer(node, source, base - width - 1) == 'b')
					moves.add(new Move(player, source, base - width - 1, this));

				if (col < width && getPlayer(node, source, base - width + 1) == 'b')
					moves.add(new Move(player, source, base - width + 1, this));
			}

			if (row > 1) {
				moves.add(new Move(player, source, base, this));

				if (row % 2 == 0) {
					if (col > 1)
						moves.add(new Move(player, source, base - 1, this));
				} else if (col < width)
					moves.add(new Move(player, source, base + 1, this));
			}

			// If king, add king moves
			if (isKing(node, source)) {
				base = source + width;

				if (row < height * 2 - 1) {
					if (col < width && getPlayer(node, source, base + width + 1) == 'b')
						moves.add(new Move(player, source, base + width + 1, this));

					if (col > 1 && getPlayer(node, source, base + width - 1) == 'b')
						moves.add(new Move(player, source, base + width - 1, this));
				}

				if (row < height * 2) {
					moves.add(new Move(player, source, base, this));

					if (row % 2 == 0) {
						if (col > 1)
							moves.add(new Move(player, source, base - 1, this));
					} else if (col < width)
						moves.add(new Move(player, source, base + 1, this));
				}
			}
		}

		return moves;
	}
	public ArrayList<Move> getAllLegalMoves(DraughtsNode node) {
		ArrayList<Move> moves = getAllLegalMovesPlayer(node, 'w');
		moves.addAll(getAllLegalMovesPlayer(node, 'b'));

		return moves;
	}
	
	// Get all the Legal moves for a given player
	public ArrayList<Move> getAllLegalMovesPlayer(DraughtsNode node, char player) {
		ArrayList<Move> moves = new ArrayList<>(),
						movesNoCapture = new ArrayList<>();	
			
		for (int i = 1; i <= totalSpots; i++) {
			if (getPlayer(node, i) == player) {
				ArrayList<Move> legalMoves = getLegalMoves(node, i);

				for (Move m : legalMoves) {
					if (this.isLegalMove(node, m)) {							
						if (m.isCapture())
							moves.add(m);
						else
							movesNoCapture.add(m);
					}
				}
			}
		}

		return moves.size() > 0 ? moves : movesNoCapture;
	}

	// Actual Movement Commands
	public boolean checkIfLegalNextMove(Move move) {
		for (DraughtsNode child : root.children) {
			if (child.move.equals(move))
				return true;
		}

		return false;
	}
	public void updateRoot(Move move) {
		for(DraughtsNode child : root.children) {
			if(child.move.equals(move)) {
				if (move.isCapture()) {
					if (move.getPlayer() == 'w')
						whiteScore++;
					else
						blackScore++;
				}
				
				this.root = child;
				root.depth = 0;
				root.movesSinceCap = 0;
				root.children = new ArrayList<DraughtsNode>();
				
				this.populate();
				
				return;
			}
		}
	}
	// Recursive Populate -- Don't use, causes stack overflow.

	public void populate() {
		System.out.println("Populating Tree... ");
		long startTime = System.currentTimeMillis();
		root.populate();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Done in: " + totalTime + "ms");
	}

	public int getRow(int position) {
		return ((position - 1) / width) + 1;
	}

	public int getColumn(int position) {
		return (position - 1) % height + 1;
	}

	public int getPositionBetween(int source, int destination) {
		int min = Math.min(source, destination), max = Math.max(source, destination);

		return min + (max - min) / 2 + getRow(source) % 2;
	}

	public DraughtsNode getRoot() {
		return root;
	}

	public void setRoot(DraughtsNode root) {
		this.root = root;
	}
}
