package view;

import game.Minesweeper;

import javax.swing.*;
import java.awt.*;

public class MineGUI extends JFrame {
    private Minesweeper game;
    private JButton board[][];
    private JPanel mainPanel;
    private final int SQUARE_SIZE = 25; // each cell with be a 10x10 pixel square


    public MineGUI() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game = new Minesweeper();
        mainPanel = new JPanel(new GridLayout(game.getNumRows(), game.getNumCols()));
        createButtons();
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    private void createButtons() {
        board = new JButton[game.getNumRows()][game.getNumCols()];
        for(int row = 0; row < game.getNumRows(); ++row) {
            for(int col = 0; col < game.getNumCols(); ++col) {
                board[row][col] = new JButton("");
                board[row][col].setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                board[row][col].getModel().setPressed(false);
                board[row][col].setMargin(new Insets(0, 0, 0, 0));
                board[row][col].setBorderPainted(true);
                board[row][col].setContentAreaFilled(false);
                mainPanel.add(board[row][col]);
            }
        }
    }
}
