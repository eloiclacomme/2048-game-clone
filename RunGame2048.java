
/**
 * Some code from this project was guided by the TicTacToe Demo provided in class
 * Credit:
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunGame2048 implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("2048");
        frame.setLocation(400, 400);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard2048 board = new GameBoard2048(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());

        control_panel.add(reset);

        // Info button
        final JButton info = new JButton("Info");

        AtomicBoolean isOpen = new AtomicBoolean(false);

        info.addActionListener(e -> {
            JFrame innerFrame = new JFrame("Game Instructions");
            if (!isOpen.get()) {
                isOpen.set(true);

                innerFrame.setFocusable(false);
                innerFrame.setLocation(200, 350);
                innerFrame.setSize(420, 230);

                String instructions = "<html> Welcome to 2048! In this game you must press the " +
                        "arrow keys which will cause all the pieces to slide that direction. If two"
                        +
                        " pieces of the same number collide, they will merge and add together." +
                        " After every move, a new piece will appear. Try to reach the 2048 tile " +
                        "before having no more moves left. An undo button has been added to undo " +
                        "your last move, and a save feature has been added to allow you to save " +
                        "and then return to a previous game. Good luck!<html>";

                JLabel text = new JLabel(instructions);

                innerFrame.add(text);

                board.requestFocusInWindow();

                innerFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                        board.requestFocusInWindow();
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        board.requestFocus();
                        isOpen.set(false);
                    }
                });

                innerFrame.setVisible(true);
            } else {
                board.requestFocusInWindow();
            }
        });
        control_panel.add(info);

        // Undo button
        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> {
            board.undo();
        });

        control_panel.add(undo);

        // Save button
        final JButton save = new JButton("Save");
        save.addActionListener(e -> {
            board.save();
        });

        control_panel.add(save);

        // Reload button
        final JButton reload = new JButton("Reload");
        reload.addActionListener(e -> {
            board.reload();
        });

        control_panel.add(reload);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}