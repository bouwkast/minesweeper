package view;

import game.Minesweeper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class MineGUI extends JFrame {
//    private Minesweeper game;
    private int numRows, numCols;
    private JButton board[][];
    private JPanel mainPanel;
    private final int SQUARE_SIZE = 25; // each cell with be a 10x10 pixel square
    private JMenuItem restart;
    private JMenuBar menuBar;
    private JMenu fileItem;
    private JPanel bombsLeftPanel;
    private JLabel bombsLeft;

    public MineGUI(Minesweeper game) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        numRows = game.getNumRows();
        numCols = game.getNumCols();

        mainPanel = new JPanel(new GridLayout(numRows, numCols));
        createButtons(game);
        initializeFileMenu();
        initializeBombsLeftPanel(game);

        this.add(mainPanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    private void initializeBombsLeftPanel(Minesweeper game) {
        bombsLeftPanel = new JPanel(new FlowLayout());
        bombsLeft = new JLabel("Bombs Left: " + game.getNumMarks());
        bombsLeftPanel.add(bombsLeft);
        bombsLeftPanel.setPreferredSize(new Dimension(SQUARE_SIZE * numCols, SQUARE_SIZE));
        this.add(bombsLeftPanel, BorderLayout.NORTH);
    }

    private void initializeFileMenu() {
        menuBar = new JMenuBar();
        restart = new JMenuItem("Restart Game");
        fileItem = new JMenu("File");

        fileItem.add(restart);
        menuBar.add(restart);

        this.add(menuBar);
        this.setJMenuBar(menuBar);
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
                board[row][col].setBackground(Color.WHITE);
                mainPanel.add(board[row][col]);
            }
        }
    }

    public void resetButtons() {
        for(int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                board[row][col].setText("");
                board[row][col].setBackground(Color.WHITE);
            }
        }
    }

    public void findBombs(Minesweeper game) {
        for(int row = 0; row < game.getNumRows(); ++row) {
            for (int col = 0; col < game.getNumCols(); ++col) {
                if(game.getGrid()[row][col].isBomb()) {
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

    public void addMineListener(ActionListener listener, MouseListener mouseListener) {
        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                board[row][col].addActionListener(listener);
                board[row][col].addMouseListener(mouseListener);
            }
        }
        restart.addActionListener(listener);
    }

    public void revealButtons(Minesweeper game) {
        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                if(!game.getGrid()[row][col].isBomb() && !game.getGrid()[row][col].isMarked() && game.getGrid()[row][col].isRevealed()) {
                   if(game.getCellAt(row, col).getNeighborMines() != 0)
                       board[row][col].setText("" + game.getCellAt(row, col).getNeighborMines());
                    else
                       board[row][col].setText("");
                }
                if(game.getGrid()[row][col].isRevealed() && !game.getGrid()[row][col].isMarked()) {
                    board[row][col].setBackground(Color.LIGHT_GRAY);
                } else {
                    board[row][col].setBackground(Color.WHITE);
                }
            }
        }
    }

    public JMenuItem getRestart() {
        return restart;
    }

    public void disableBoard() {
        for(int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                board[row][col].setEnabled(false);
            }
        }
    }

    public void enableBoard() {
        for(int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                board[row][col].setEnabled(true);
            }
        }
    }

    public void changeBombsLeft(int numBombs) {
        bombsLeft.setText("Bombs Left: " + numBombs);
    }
}
