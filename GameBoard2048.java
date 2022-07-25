
/**
 * Some code from this project was guided by the TicTacToe Demo provided in class
 * Credit:
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class GameBoard2048 extends JPanel {

    private Play2048 board; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public GameBoard2048(JLabel status) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setFocusable(true);

        board = new Play2048(); // initializes model for the game
        this.status = status; // initializes the status JLabel

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    board.shiftBoard(Shift.LEFT);
                    repaint();
                    updateStatus();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    board.shiftBoard(Shift.RIGHT);
                    repaint();
                    updateStatus();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    board.shiftBoard(Shift.UP);
                    repaint();
                    updateStatus();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    board.shiftBoard(Shift.DOWN);
                    repaint();
                    updateStatus();
                }
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        board.reset();
        status.setText("Ready?");
        repaint();

        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        status.setText("Number of moves: " + board.getNumTurns());
    }

    public void undo() {
        board.undo();
        repaint();
        updateStatus();

        requestFocusInWindow();
    }

    public void save() {
        board.save();
        requestFocusInWindow();
    }

    public void reload() {
        board.reload();
        repaint();
        updateStatus();

        requestFocusInWindow();
    }

    /**
     * Draws the game board
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.drawLine(100, 0, 100, 400);
        g.drawLine(200, 0, 200, 400);
        g.drawLine(300, 0, 300, 400);
        g.drawLine(0, 100, 400, 100);
        g.drawLine(0, 200, 400, 200);
        g.drawLine(0, 300, 400, 300);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int state = board.getCell(j, i);
                setPenColor(state, g);
                g.fillRect(1 + 100 * i, 1 + 100 * j, 99, 99);
                g.setColor(Color.BLACK);
                drawNumber(state, i, j, g);
            }
        }
        if (board.isWin()) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Merryweather", Font.PLAIN, 60));
            g.drawString("YOU WIN!", 52, 215);
        } else if (board.isGameOver()) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Merryweather", Font.PLAIN, 60));
            g.drawString("GAME OVER", 20, 215);
        }
    }

    /**
     * Method that sets the pen color according to the tile of the value
     * to paint its background
     * 
     * @param tileVal value of the tile
     * @param g       graphics context
     */
    public void setPenColor(int tileVal, Graphics g) {
        if (tileVal == 0) {
            g.setColor(new Color(203, 194, 179));
        } else if (tileVal == 2) {
            g.setColor(new Color(238, 228, 218));
        } else if (tileVal == 4) {
            g.setColor(new Color(236, 224, 200));
        } else if (tileVal == 8) {
            g.setColor(new Color(240, 177, 121));
        } else if (tileVal == 16) {
            g.setColor(new Color(243, 151, 104));
        } else if (tileVal == 32) {
            g.setColor(new Color(235, 129, 102));
        } else if (tileVal == 64) {
            g.setColor(new Color(244, 96, 66));
        } else if (tileVal == 128) {
            g.setColor(new Color(234, 207, 118));
        } else if (tileVal == 256) {
            g.setColor(new Color(236, 204, 101));
        } else if (tileVal == 512) {
            g.setColor(new Color(230, 200, 76));
        } else {
            g.setColor(new Color(229, 193, 73));
        }
    }

    /**
     * Method that draws the numbers on the board
     * 
     * @param tileVal value of the tile
     * @param x       row of tile
     * @param y       column of tile
     * @param g       graphics context
     */
    public void drawNumber(int tileVal, int x, int y, Graphics g) {
        if (tileVal >= 1000) {
            g.setFont(new Font("ClearSans", Font.PLAIN, 42));
            g.setColor(new Color(251, 247, 238));
            g.drawString("" + tileVal, 5 + 100 * x, 65 + 100 * y);
        } else if (tileVal >= 100) {
            g.setFont(new Font("ClearSans", Font.PLAIN, 50));
            g.setColor(new Color(251, 247, 238));
            g.drawString("" + tileVal, 8 + 100 * x, 70 + 100 * y);
        } else if (tileVal >= 10) {
            g.setFont(new Font("ClearSans", Font.PLAIN, 60));
            g.setColor(new Color(251, 247, 238));
            g.drawString("" + tileVal, 19 + 100 * x, 74 + 100 * y);
        } else if (tileVal != 0) {
            g.setFont(new Font("ClearSans", Font.PLAIN, 60));
            if (tileVal == 8) {
                g.setColor(new Color(251, 247, 238));
            } else {
                g.setColor(new Color(113, 105, 90));
            }
            g.drawString("" + tileVal, 34 + 100 * x, 74 + 100 * y);
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    /**
     * Methods for testing purposes. See Play2048.java for details about specific
     * methods
     */
    public void customBoard() {
        board.customBoard();
    }

    public void customBoard(int[][] arr) {
        board.customBoard(arr);
    }

    public void addTile(int num, int xpos, int ypos) {
        board.addTile(num, xpos, ypos);
    }

    public void flipAddNewBlock() {
        board.flipAddNewBlock();
    }

    public int[][] arrayCopy(int[][] arr) {
        return board.arrayCopy(arr);
    }

    public int getCell(int row, int col) {
        return board.getCell(row, col);
    }

    public void shiftBoard(Shift d) {
        board.shiftBoard(d);
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public boolean isWin() {
        return board.isWin();
    }

    public boolean isFullBoard() {
        return board.isFullBoard();
    }

    public int[][] getBoard() {
        int[][] arr = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr[i][j] = board.getCell(i, j);
            }
        }
        return arr;
    }
}
