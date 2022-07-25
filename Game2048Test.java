
import org.junit.jupiter.api.*;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.*;
import java.util.Arrays;

public class Game2048Test {

    @Test
    public void testSlideFourDirectionsSingleton() {

        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);
        board.customBoard();

        board.addTile(2, 0, 0);
        assertEquals(2, board.getCell(0, 0));

        board.shiftBoard(Shift.DOWN);
        assertEquals(0, board.getCell(0, 0));
        assertEquals(2, board.getCell(3, 0));

        board.shiftBoard(Shift.RIGHT);
        assertEquals(0, board.getCell(3, 0));
        assertEquals(2, board.getCell(3, 3));

        board.shiftBoard(Shift.UP);
        assertEquals(0, board.getCell(3, 3));
        assertEquals(2, board.getCell(0, 3));

        board.shiftBoard(Shift.LEFT);
        assertEquals(0, board.getCell(0, 3));
        assertEquals(2, board.getCell(0, 0));

    }

    @Test
    public void testSlideFourDirectionsThreeBlocks() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 4, 0, 0 }, { 8, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.DOWN);
        assertEquals(2, board.getCell(2, 0));
        assertEquals(4, board.getCell(3, 1));
        assertEquals(8, board.getCell(3, 0));

        board.shiftBoard(Shift.RIGHT);
        assertEquals(2, board.getCell(2, 3));
        assertEquals(4, board.getCell(3, 3));
        assertEquals(8, board.getCell(3, 2));

        board.shiftBoard(Shift.UP);
        assertEquals(2, board.getCell(0, 3));
        assertEquals(4, board.getCell(1, 3));
        assertEquals(8, board.getCell(0, 2));

        board.shiftBoard(Shift.LEFT);
        assertEquals(2, board.getCell(0, 1));
        assertEquals(4, board.getCell(1, 0));
        assertEquals(8, board.getCell(0, 0));
    }

    @Test
    public void testSlideFourDirectionsMultipleBlocks() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 0, 0, 8, 0 }, { 0, 2, 0, 0 }, { 0, 4, 0, 0 }, { 0, 0, 0, 0 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.DOWN);
        assertEquals(2, board.getCell(2, 1));
        assertEquals(4, board.getCell(3, 1));
        assertEquals(8, board.getCell(3, 2));

        board.shiftBoard(Shift.RIGHT);
        assertEquals(2, board.getCell(2, 3));
        assertEquals(4, board.getCell(3, 2));
        assertEquals(8, board.getCell(3, 3));

        board.shiftBoard(Shift.UP);
        assertEquals(2, board.getCell(0, 3));
        assertEquals(4, board.getCell(0, 2));
        assertEquals(8, board.getCell(1, 3));

        board.shiftBoard(Shift.LEFT);
        assertEquals(2, board.getCell(0, 1));
        assertEquals(4, board.getCell(0, 0));
        assertEquals(8, board.getCell(1, 0));
    }

    @Test
    public void testCombineLeftAllTwoCombinationsAndFourCombination() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        // each row represents a possible "combination" of how two blocks could merge
        int[][] arr = { { 2, 2, 0, 0 }, { 0, 2, 2, 0 }, { 0, 0, 2, 2 }, { 2, 2, 2, 2 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.LEFT);
        int[][] newArr = { { 4, 0, 0, 0 }, { 4, 0, 0, 0 }, { 4, 0, 0, 0 }, { 4, 4, 0, 0 } };
        assertArrayEquals(board.getBoard(), newArr);
    }

    @Test
    public void testCombineLeftAllThreeCombinations() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 2, 0, 2 }, { 2, 0, 2, 2 }, { 2, 2, 2, 0 }, { 0, 2, 2, 2 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.LEFT);
        int[][] newArr = { { 4, 2, 0, 0 }, { 4, 2, 0, 0 }, { 4, 2, 0, 0 }, { 4, 2, 0, 0 } };
        assertArrayEquals(board.getBoard(), newArr);
    }

    @Test
    public void testCombineRightAllTwoCombinationsAndFourCombination() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        // each row represents a possible "combination" of how two blocks could merge
        int[][] arr = { { 2, 2, 0, 0 }, { 0, 2, 2, 0 }, { 0, 0, 2, 2 }, { 2, 2, 2, 2 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.RIGHT);
        int[][] newArr = { { 0, 0, 0, 4 }, { 0, 0, 0, 4 }, { 0, 0, 0, 4 }, { 0, 0, 4, 4 } };
        assertArrayEquals(board.getBoard(), newArr);
    }

    @Test
    public void testCombineRightAllThreeCombinations() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 2, 0, 2 }, { 2, 0, 2, 2 }, { 2, 2, 2, 0 }, { 0, 2, 2, 2 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.RIGHT);
        int[][] newArr = { { 0, 0, 2, 4 }, { 0, 0, 2, 4 }, { 0, 0, 2, 4 }, { 0, 0, 2, 4 } };
        assertArrayEquals(board.getBoard(), newArr);
    }

    @Test
    public void testCombineUpAllTwoCombinationsAndFourCombination() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        // each row represents a possible "combination" of how two blocks could merge
        int[][] arr = { { 2, 2, 0, 0 }, { 0, 2, 2, 0 }, { 0, 2, 2, 2 }, { 2, 2, 2, 2 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.UP);
        int[][] newArr = { { 4, 4, 4, 4 }, { 0, 4, 2, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        assertArrayEquals(board.getBoard(), newArr);
    }

    @Test
    public void testCombineUpAllThreeCombinations() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 2, 0, 2 }, { 2, 0, 2, 2 }, { 2, 2, 2, 0 }, { 0, 2, 2, 2 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.UP);
        int[][] newArr = { { 4, 4, 4, 4 }, { 2, 2, 2, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        assertArrayEquals(board.getBoard(), newArr);
    }

    @Test
    public void testCombineDownAllTwoCombinationsAndFourCombination() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        // each row represents a possible "combination" of how two blocks could merge
        int[][] arr = { { 2, 2, 0, 0 }, { 0, 2, 2, 0 }, { 0, 2, 2, 2 }, { 2, 2, 2, 2 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.DOWN);
        int[][] newArr = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 4, 2, 0 }, { 4, 4, 4, 4 } };
        assertArrayEquals(board.getBoard(), newArr);
    }

    @Test
    public void testCombineDownAllThreeCombinations() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 2, 0, 2 }, { 2, 0, 2, 2 }, { 2, 2, 2, 0 }, { 0, 2, 2, 2 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.DOWN);
        int[][] newArr = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 2, 2, 2 }, { 4, 4, 4, 4 } };
        assertArrayEquals(board.getBoard(), newArr);
    }

    @Test
    public void testCombineEdgeCases() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 4, 4, 2, 2 }, { 4, 2, 2, 0 }, { 4, 0, 2, 2 }, { 2, 4, 2, 0 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.LEFT);
        int[][] newArr = { { 8, 4, 0, 0 }, { 4, 4, 0, 0 }, { 4, 4, 0, 0 }, { 2, 4, 2, 0 } };
        assertArrayEquals(board.getBoard(), newArr);
    }

    @Test
    public void testGameOverTrue() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 4, 2 } };
        board.customBoard(arr);

        assertTrue(board.isGameOver());
    }

    @Test
    public void testFullBoardFalse() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 4, 0 } };
        board.customBoard(arr);

        assertFalse(board.isFullBoard());
    }

    @Test
    public void testFullBoardTrue() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 4, 0 } };
        board.customBoard(arr);
        board.flipAddNewBlock();
        board.shiftBoard(Shift.DOWN);

        assertTrue(board.isFullBoard());
    }

    @Test
    public void testWinFalse() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 1024, 1024 } };
        board.customBoard(arr);

        assertFalse(board.isWin());
    }

    @Test
    public void testWinTrue() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 1024, 1024 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.RIGHT);

        assertTrue(board.isWin());
    }

    @Test
    public void testWinTrueAndGameOverTrue() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 2, 4 }, { 4, 2, 4, 2048 } };
        board.customBoard(arr);

        assertTrue(board.isWin());
        assertTrue(board.isGameOver());
    }

    @Test
    public void testNewBlockAfterValidMove() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 0, 2, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        board.customBoard(arr);
        board.flipAddNewBlock();

        board.shiftBoard(Shift.DOWN);

        int numberOfTwosOrFours = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board.getCell(i, j) == 2 || board.getCell(i, j) == 4) {
                    numberOfTwosOrFours++;
                }
            }
        }
        assertEquals(2, numberOfTwosOrFours);
    }

    @Test
    public void testNewBlockAfterMoveThatDoesntDoAnything() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 0, 0, 0 } };
        board.customBoard(arr);
        board.flipAddNewBlock();

        board.shiftBoard(Shift.DOWN);

        int numberOfTwosOrFours = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board.getCell(i, j) == 2 || board.getCell(i, j) == 4) {
                    numberOfTwosOrFours++;
                }
            }
        }
        assertEquals(1, numberOfTwosOrFours);
    }

    @Test
    public void testNewBlockAfterFullBoard() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 2, 4, 2, 4 }, { 4, 2, 4, 2 }, { 2, 4, 8, 16 }, { 4, 2, 4, 4 } };
        board.customBoard(arr);
        board.flipAddNewBlock();

        board.shiftBoard(Shift.LEFT);

        int numberOfTwosOrFours = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board.getCell(i, j) == 2 || board.getCell(i, j) == 4) {
                    numberOfTwosOrFours++;
                }
            }
        }
        assertEquals(13, numberOfTwosOrFours);
    }

    @Test
    public void testResetAfterNoMoves() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 0, 0, 2, 0 }, { 0, 0, 0, 0 }, { 0, 4, 0, 0 }, { 2, 0, 0, 0 } };
        board.customBoard(arr);
        board.flipAddNewBlock();

        board.reset();

        assertFalse(Arrays.deepEquals(arr, board.getBoard()));
    }

    @Test
    public void testResetAfterMoves() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 0, 0, 2, 0 }, { 0, 0, 0, 0 }, { 0, 4, 0, 0 }, { 2, 0, 0, 0 } };
        board.customBoard(arr);
        board.flipAddNewBlock();

        board.shiftBoard(Shift.LEFT);
        board.shiftBoard(Shift.DOWN);
        board.shiftBoard(Shift.RIGHT);
        board.reset();

        assertFalse(Arrays.deepEquals(arr, board.getBoard()));
    }

    @Test
    public void testUndoAfterSingleMove() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 0, 0, 2, 0 }, { 0, 0, 0, 0 }, { 0, 4, 0, 0 }, { 2, 0, 0, 0 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.LEFT);
        int[][] newArr = { { 2, 0, 0, 0 }, { 0, 0, 0, 0 }, { 4, 0, 0, 0 }, { 2, 0, 0, 0 } };

        assertArrayEquals(board.getBoard(), newArr);

        board.undo();

        assertArrayEquals(board.getBoard(), arr);
    }

    @Test
    public void testUndoAfterMultipleMoves() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 0, 0, 2, 0 }, { 0, 0, 0, 0 }, { 0, 4, 0, 0 }, { 2, 0, 0, 0 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.LEFT);
        int[][] newArr = { { 2, 0, 0, 0 }, { 0, 0, 0, 0 }, { 4, 0, 0, 0 }, { 2, 0, 0, 0 } };
        assertArrayEquals(board.getBoard(), newArr);

        board.shiftBoard(Shift.UP);
        int[][] newArr2 = { { 2, 0, 0, 0 }, { 4, 0, 0, 0 }, { 2, 0, 0, 0 }, { 0, 0, 0, 0 } };
        assertArrayEquals(board.getBoard(), newArr2);

        board.undo();
        assertArrayEquals(board.getBoard(), newArr);

        board.undo();
        assertArrayEquals(board.getBoard(), arr);
    }

    @Test
    public void testUndoAfterNoMoves() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 0, 0, 2, 0 }, { 0, 0, 0, 0 }, { 0, 4, 0, 0 }, { 2, 0, 0, 0 } };
        board.customBoard(arr);

        board.undo();
        assertArrayEquals(board.getBoard(), arr);
    }

    @Test
    public void testUndoAfterReset() {
        final JLabel status = new JLabel("Setting up...");
        final GameBoard2048 board = new GameBoard2048(status);

        int[][] arr = { { 0, 0, 2, 0 }, { 0, 0, 0, 0 }, { 0, 4, 0, 0 }, { 2, 0, 0, 0 } };
        board.customBoard(arr);

        board.shiftBoard(Shift.LEFT);
        int[][] newArr = { { 2, 0, 0, 0 }, { 0, 0, 0, 0 }, { 4, 0, 0, 0 }, { 2, 0, 0, 0 } };

        assertArrayEquals(board.getBoard(), newArr);

        board.reset();
        board.undo();

        assertFalse(Arrays.deepEquals(arr, board.getBoard()));
    }

}
