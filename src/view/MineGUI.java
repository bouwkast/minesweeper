package view;

import game.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MineGUI extends JFrame {
//    private Minesweeper game;
    private int numRows, numCols;
    private JButton board[][];
    private JPanel mainPanel;
    private final int SQUARE_SIZE = 25; // each cell with be a 10x10 pixel square


    public MineGUI(Minesweeper game) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        numRows = game.getNumRows();
        numCols = game.getNumCols();
        mainPanel = new JPanel(new GridLayout(numRows, numCols));
        createButtons(game);

        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    private void createButtons(Minesweeper game) {
        board = new JButton[game.getNumRows()][game.getNumCols()];
        for(int row = 0; row < game.getNumRows(); ++row) {
            for(int col = 0; col < game.getNumCols(); ++col) {
                board[row][col] = new JButton("");
                board[row][col].setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                board[row][col].getModel().setPressed(false);
                board[row][col].setMargin(new Insets(0, 0, 0, 0));
                board[row][col].setBorderPainted(true);
                board[row][col].setContentAreaFilled(true);
                mainPanel.add(board[row][col]);
            }
        }
    }

    public void findBombs(Minesweeper game) {
        for(int row = 0; row < game.getNumRows(); ++row) {
            for (int col = 0; col < game.getNumCols(); ++col) {
                if(game.getGrid()[row][col].isBomb()) {
//                    System.out.println("YEP");
                    board[row][col].setText("B");
                }
            }
        }
    }

    public JButton[][] getBoard() {
        return board;
    }

    public JButton getButtonAt(int row, int col) {
        return board[row][col];
    }

    public void addMineListener(ActionListener listener) {
        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                board[row][col].addActionListener(listener);
            }
        }
    }

    public void revealButtons(Minesweeper game) {
        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                if(!game.getGrid()[row][col].isBomb()) {
                   board[row][col].setText("" + game.getCellAt(row, col).getNeighborMines());
                }
                if(game.getGrid()[row][col].isRevealed()) {
                    board[row][col].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }
}
