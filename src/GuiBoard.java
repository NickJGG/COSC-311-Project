import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class GuiBoard {
	JFrame f;
	JPanel mainPanel;
	JPanel infoPanel;
	
	Draughts d;

	// Custom JPanel class for the spaces on the board
	private static class SpacePanel extends JPanel implements MouseListener {
		public static final int NONE = 0;
		public static final int WHITE_MAN = 1;
		public static final int BLACK_MAN = 2;
		public static final int WHITE_KING = 3;
		public static final int BLACK_KING = 4;

		private static ImageIcon whiteMan;
		private static ImageIcon blackMan;
		private static ImageIcon whiteKing;
		private static ImageIcon blackKing;
		
		
		static int firstSelected = 0;
		static int secondSelected = 0;
		
		private int position;
		
		private GuiBoard board;

		public static void init() throws IOException {
			whiteMan = new ImageIcon(
					ImageIO.read(SpacePanel.class.getClassLoader().getResource("resources/WhiteMan.png")));
			blackMan = new ImageIcon(
					ImageIO.read(SpacePanel.class.getClassLoader().getResource("resources/BlackMan.png")));
			whiteKing = new ImageIcon(
					ImageIO.read(SpacePanel.class.getClassLoader().getResource("resources/WhiteKing.png")));
			blackKing = new ImageIcon(
					ImageIO.read(SpacePanel.class.getClassLoader().getResource("resources/BlackKing.png")));
		}

		public SpacePanel(int position, GuiBoard board) {
			this.position = position;
			this.board = board;
			this.addMouseListener(this);
		}

		// update's the spaces Icon
		public void update(int piece) {
			this.removeAll();
			
			if (piece == WHITE_MAN)
				this.add(new JLabel(whiteMan));
			else if (piece == BLACK_MAN)
				this.add(new JLabel(blackMan));
			else if (piece == WHITE_KING)
				this.add(new JLabel(whiteKing));
			else if (piece == BLACK_KING)
				this.add(new JLabel(blackKing));
		}

		public int getPosition() {
			return this.position;
		}

		public void mouseClicked(MouseEvent e) {
			if(firstSelected != 0 && secondSelected == 0) {
				secondSelected = position;
				
				System.out.println(position);
				
				board.d.move(firstSelected, secondSelected);
				board.update();
			} else if (board.d.pieceExists(position)){
				firstSelected = position;
				secondSelected = 0;
				
				System.out.print(position + " > ");
			}
		}
		
		//Unused, but must be implemented to prevent compile errors
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

	}

	SpacePanel[] spaces = new SpacePanel[32];

	public GuiBoard(Draughts d) {
		this.d = d;
		createAndShow();
		try {
			SpacePanel.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createAndShow() {
		f = new JFrame("Checkers");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel borderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		borderPanel.setBackground(Color.WHITE);

		mainPanel = new JPanel(new GridLayout(8, 8));
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		for (int i = 0; i < 32; i++) {
			JPanel dummy = new JPanel();
			spaces[i] = new SpacePanel(i + 1, this);

			dummy.setPreferredSize(new Dimension(40, 40));
			spaces[i].setPreferredSize(new Dimension(40, 40));

			// new Color(56, 187, 0), new Color(251, 214, 114)

			dummy.setBackground(new Color(255, 255, 255));
			spaces[i].setBackground(new Color(200, 200, 200));

			if ((i / 4) % 2 == 0) {
				mainPanel.add(dummy);
				mainPanel.add(spaces[i]);
			} else {
				mainPanel.add(spaces[i]);
				mainPanel.add(dummy);
			}
		}
		
		
		// Information Panel on the current game. Might be useful for stuff later.
		infoPanel = new JPanel(new BorderLayout());
		infoPanel.setPreferredSize(new Dimension(160, 322));
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		infoPanel.setBackground(Color.WHITE);
		
		JPanel infoTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
		infoTitle.add(new JLabel("Information"));
		infoTitle.setBackground(Color.WHITE);
		infoPanel.add(infoTitle, BorderLayout.PAGE_START);

		f.add(borderPanel);
		borderPanel.add(mainPanel);
		borderPanel.add(infoPanel);

		f.pack();
		f.setVisible(true);
	}

	public void update() {
		for (int i = 1; i <= 32; i++) {
			int digit = 1 << (32 - i);
			
			if ((digit & d.getWhites()) != 0) {
				spaces[i - 1].update(SpacePanel.WHITE_MAN);
				
				if ((digit & d.getKings()) != 0)
					spaces[i - 1].update(SpacePanel.WHITE_KING);
			} else if ((digit & d.getBlacks()) != 0) {
				spaces[i - 1].update(SpacePanel.BLACK_MAN);
				
				if ((digit & d.getKings()) != 0)
					spaces[i - 1].update(SpacePanel.BLACK_KING);
			} else
				spaces[i - 1].update(SpacePanel.NONE);
		}
		
		f.pack();
		f.repaint();
	}
}
