
/**
 * Some code from this project was guided by the TicTacToe Demo provided in class
 * Credit:
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class is a model for 2048.
 */
public class Play2048 {

    private int numTurns;
    private boolean isTestingGameOver; // we have to run the shiftBoard method when
                                       // testing whether the game is over or not
                                       // but we do not want these boards to be saved
    private boolean isAddNewBlock; // for testing purposes, will not add a
                                   // new block if a move is made to make
                                   // the state of the board predictable
    private int[][] board;

    private LinkedList<int[][]> boardList;

    /**
     * Constructor sets up game state.
     */
    public Play2048() {
        reset();
    }

    /**
     * Method that shifts the pieces of the board and handles all the logic
     * that comes with moving tiles and merging tiles, as well
     * as updating the state of the game as necessary
     * 
     * @param d the move that the player makes which corresponds to the direction
     *          that the board will shift to
     */
    public void shiftBoard(Shift d) {
        int[][] lastBoard;
        if (!isWin()) {
            lastBoard = arrayCopy(board);

            // separates down/right and up/left directions since cases must be handled
            // differently
            if (d == Shift.DOWN || d == Shift.RIGHT) {

                // array has to be looped backwards
                for (int i = board.length - 1; i >= 0; i--) {
                    for (int j = board[i].length - 1; j >= 0; j--) {

                        // if tile is empty
                        if (board[i][j] != 0) {
                            // down case and last row cannot move down
                            if (d == Shift.DOWN && i != 3) {
                                // checking for adjacent merging
                                if (board[i + 1][j] == board[i][j]) {
                                    board[i + 1][j] = board[i][j] * 2;
                                    board[i][j] = 0;
                                } else { // sliding down
                                    int openSpot = board.length - 1;
                                    // checks for first open spot
                                    while (openSpot > i && board[openSpot][j] != 0) {
                                        openSpot--;
                                    }
                                    // if this spot is further down than the tile
                                    if (openSpot > i) {
                                        // if in bounds and is the same tile then merge
                                        if (openSpot + 1 <= 3 &&
                                                (board[openSpot + 1][j] == board[i][j])) {
                                            board[openSpot + 1][j] = board[i][j] * 2;
                                        } else { // no merge, just slide tile
                                            board[openSpot][j] = board[i][j];
                                        }
                                        // replace previous spot with empty tile
                                        board[i][j] = 0;
                                    }
                                } // right case and last column cannot move down
                            } else if (d == Shift.RIGHT && j != 3) {
                                // logic is the same except now everything has to do with columns
                                // rather than rows
                                if (board[i][j + 1] == board[i][j]) { // checks for adjacent merging
                                    board[i][j + 1] = board[i][j] * 2;
                                    board[i][j] = 0;
                                } else { // sliding right
                                    int openSpot = board.length - 1;
                                    // checks for first open spot
                                    while (openSpot > j && board[i][openSpot] != 0) {
                                        openSpot--;
                                    }
                                    // if this spot is further right than the tile
                                    if (openSpot > j) {
                                        // if in bounds and is the same tile then merge
                                        if (openSpot + 1 <= 3 &&
                                                (board[i][openSpot + 1] == board[i][j])) {
                                            board[i][openSpot + 1] = board[i][j] * 2;
                                        } else { // no merge, just slide tile
                                            board[i][openSpot] = board[i][j];
                                        }
                                        // replace previous spot with empty tile
                                        board[i][j] = 0;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!Arrays.deepEquals(lastBoard, board)) { // if the board changed
                    if (isAddNewBlock) { // for testing purposes
                        newBlock(); // adds a new tile to an open spot
                    }

                    if (!isTestingGameOver) { // if we are not testing a game over
                        numTurns++;
                        boardList.addFirst(lastBoard); // adds previous board to list for undo
                                                       // button
                    }
                }
            }
            if (d == Shift.UP || d == Shift.LEFT) { // up and left cases
                for (int i = 0; i < board.length; i++) { // array has to be looped forwards
                    for (int j = 0; j < board[i].length; j++) { // same logic otherwise
                        if (board[i][j] != 0) {
                            if (d == Shift.UP && i != 0) {
                                if (board[i - 1][j] == board[i][j]) {
                                    board[i - 1][j] = board[i][j] * 2;
                                    board[i][j] = 0;
                                } else {
                                    int openSpot = 0;
                                    while (openSpot < i && board[openSpot][j] != 0) {
                                        openSpot++;
                                    }
                                    if (openSpot < i) {
                                        if (openSpot - 1 >= 0 &&
                                                (board[openSpot - 1][j] == board[i][j])) {
                                            board[openSpot - 1][j] = board[i][j] * 2;
                                        } else {
                                            board[openSpot][j] = board[i][j];
                                        }
                                        board[i][j] = 0;
                                    }
                                }
                            } else if (d == Shift.LEFT && j != 0) {
                                if (board[i][j - 1] == board[i][j]) {
                                    board[i][j - 1] = board[i][j] * 2;
                                    board[i][j] = 0;
                                } else {
                                    int openSpot = 0;
                                    while (openSpot < j && board[i][openSpot] != 0) {
                                        openSpot++;
                                    }
                                    if (openSpot < j) {
                                        if (openSpot - 1 >= 0 &&
                                                (board[i][openSpot - 1] == board[i][j])) {
                                            board[i][openSpot - 1] = board[i][j] * 2;
                                        } else {
                                            board[i][openSpot] = board[i][j];
                                        }
                                        board[i][j] = 0;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!Arrays.deepEquals(lastBoard, board)) {
                    if (isAddNewBlock) {
                        newBlock();
                    }

                    if (!isTestingGameOver) {
                        numTurns++;
                        boardList.addFirst(lastBoard);
                    }
                }
            }
            printGameState();
        }
    }

    /**
     * Method that creates a new tile if conditions are met
     */
    public void newBlock() {
        int row;
        int col;
        int numAdded;
        boolean isAdded = false;
        while (!isAdded && !isFullBoard()) {
            row = (int) (Math.random() * 4);
            col = (int) (Math.random() * 4);
            numAdded = (int) (Math.random() * 5);
            if (board[row][col] == 0) {
                if (numAdded == 0) {
                    numAdded = 4;
                } else {
                    numAdded = 2;
                }
                board[row][col] = numAdded;
                isAdded = true;
            }
        }
    }

    /**
     * A method that checks whether the board is full of tiles or not
     * 
     * @return boolean representing whether board is full or not
     */
    public boolean isFullBoard() {
        boolean isFull = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    isFull = false;
                    break;
                }
            }
        }
        return isFull;
    }

    /**
     * A method that checks whether there are no playable moves left or not
     * 
     * @return boolean representing whether the game is over or not
     */
    public boolean isGameOver() {
        if (!isFullBoard()) {
            return false;
        }

        isTestingGameOver = true;

        int[][] originalBoard = arrayCopy(board);

        shiftBoard(Shift.LEFT);
        int[][] boardShiftLeft = arrayCopy(board);
        board = arrayCopy(originalBoard);

        shiftBoard(Shift.RIGHT);
        int[][] boardShiftRight = arrayCopy(board);
        board = arrayCopy(originalBoard);

        shiftBoard(Shift.UP);
        int[][] boardShiftUp = arrayCopy(board);
        board = arrayCopy(originalBoard);

        shiftBoard(Shift.DOWN);
        int[][] boardShiftDown = arrayCopy(board);
        board = arrayCopy(originalBoard);

        isTestingGameOver = false;

        if (Arrays.deepEquals(boardShiftLeft, board) && Arrays.deepEquals(boardShiftRight, board) &&
                Arrays.deepEquals(boardShiftUp, board)
                && Arrays.deepEquals(boardShiftDown, board)) {
            board = arrayCopy(originalBoard);
            return true;
        }
        board = arrayCopy(originalBoard);
        return false;
    }

    public boolean isWin() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A method that makes a copy of an array
     * 
     * @param arr array to make a copy of
     * @return copy of that array
     */
    public int[][] arrayCopy(int[][] arr) {
        int[][] newArr = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 3) {
                    System.out.print(" | ");
                }
            }
            if (i < 3) {
                System.out.println("\n------------");
            }
        }
        System.out.println("\n");
    }

    public int getNumTurns() {
        return numTurns;
    }

    /**
     * A method that changes the game state to the previous move
     */
    public void undo() {
        if (boardList.size() > 0) {
            board = boardList.pop();
            numTurns--;
        }
    }

    /**
     * A method that saves the current game onto a file
     */
    public void save() {
        File newSave = new File("files/game2048save.txt");

        if (!newSave.exists()) {
            try {
                newSave.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fw = new FileWriter(newSave);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(numTurns + "\n");
            bw.write(isTestingGameOver + "\n");
            bw.write(isAddNewBlock + "\n");

            String strBoard = arrayToString(board);
            bw.write(strBoard, 0, strBoard.length());

            LinkedList<int[][]> boardListCopy = (LinkedList<int[][]>) boardList.clone();
            while (boardListCopy.size() > 0) {
                bw.write(arrayToString(boardListCopy.pop()));
            }

            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method that reloads a saved file if there exists one
     */
    public void reload() {
        File gameFile = new File("files/game2048save.txt");
        if (gameFile.exists()) {
            String c = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader("files/game2048save.txt"));
                String nextLine;
                numTurns = Integer.parseInt(br.readLine());
                isTestingGameOver = Boolean.parseBoolean(br.readLine());
                isAddNewBlock = Boolean.parseBoolean(br.readLine());

                nextLine = br.readLine();

                int i = 0;
                while (i < 4) {
                    c += nextLine;
                    nextLine = br.readLine();
                    i++;
                }
                board = stringToArray(c);

                String linkedListArr = "";

                i = 1;
                boardList.clear();
                while (nextLine != null) {
                    linkedListArr += nextLine;
                    if (i % 4 == 0 && i != 0) {
                        boardList.add(stringToArray(linkedListArr));
                        linkedListArr = "";
                    }
                    nextLine = br.readLine();
                    i++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(c);
        }
    }

    /**
     * Turns an int[][] to a String where each entry of the array is followed by a ,
     * and
     * the end of a row is followed by a new line
     * 
     * @param arr the int[][] that we want to turn into a String
     * @return a String representing the entries of the int[][]
     */
    public String arrayToString(int[][] arr) {
        String c = "";
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                c += arr[i][j] + ",";
            }
            c += "\n";
        }
        System.out.println(c);
        return c;
    }

    /**
     * Turns a String to an int[][]
     * 
     * @param c the String that we want to turn into an int[][]
     * @return an int[][] representing the entries of the string
     */
    public int[][] stringToArray(String c) {
        int count = 0;
        int[][] newArr = new int[4][4];

        String[] nums = c.split(",");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newArr[i][j] = Integer.parseInt(nums[count]);
                count++;
            }
        }
        return newArr;
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new int[4][4];
        boardList = new LinkedList<>();

        newBlock();
        newBlock();

        numTurns = 0;
        isAddNewBlock = true;
        isTestingGameOver = false;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param row row to retrieve
     * @param col column to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board
     */
    public int getCell(int row, int col) {
        return board[row][col];
    }

    /**
     * Only for testing purposes. To add a tile to a desired location if no tile
     * already exists there.
     *
     * @param num  number of tile
     * @param xpos row of tile
     * @param ypos column of tile
     */
    public void addTile(int num, int xpos, int ypos) {
        if (board[xpos][ypos] != 0) {
            throw new IllegalArgumentException();
        } else {
            board[xpos][ypos] = num;
        }
    }

    /**
     * Only for testing purposes. Creates a blank board without any tiles and
     * initializes
     * other global variables.
     */
    public void customBoard() {
        board = new int[4][4];
        boardList = new LinkedList<>();
        numTurns = 0;
        isAddNewBlock = false;
        isTestingGameOver = false;
    }

    /**
     * Only for testing purposes. Will negate isAddNewBlock global variable,
     * which controls whether a new tile is added after each move. This is
     * turned off when testing to be able to predict the state of the board.
     */
    public void flipAddNewBlock() {
        isAddNewBlock = !isAddNewBlock;
    }

    public void customBoard(int[][] arr) {
        board = arrayCopy(arr);
        boardList = new LinkedList<>();
        numTurns = 0;
        isAddNewBlock = false;
        isTestingGameOver = false;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Play2048 t = new Play2048();

        t.printGameState();
        System.out.println();
        System.out.println();
    }
}
