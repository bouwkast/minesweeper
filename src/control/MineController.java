package control;

import game.Minesweeper;
import view.MineGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MineController {

    private Minesweeper game;
    private MineGUI view;

    private int selRow, selCol;

    private boolean firstClick = true;

    public MineController(MineGUI view, Minesweeper game) {
        this.view = view;
        this.game = game;
        this.view.addMineListener(new MineListener(), new MineMouseListener());
    }

    class MineMouseListener implements MouseListener {

        /**
         * Invoked when the mouse button has been clicked (pressed
         * and released) on a component.
         *
         * @param e
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * Invoked when a mouse button has been pressed on a component.
         *
         * @param e
         */
        @Override
        public void mousePressed(MouseEvent e) {

        }

        /**
         * Invoked when a mouse button has been released on a component.
         *
         * @param e
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON3) {
                findCell(e);
                if(game.getGrid()[selRow][selCol].isMarked() && !game.getGrid()[selRow][selCol].isRevealed()) {
                    game.getGrid()[selRow][selCol].setMarked(false);
                    game.setNumMarks(game.getNumMarks() + 1);
                    view.getButtonAt(selRow, selCol).setText("");
                    view.changeBombsLeft(game.getNumMarks());
                } else {
                    if(game.getNumMarks() > 0 && !game.getGrid()[selRow][selCol].isRevealed()) {
                        game.getGrid()[selRow][selCol].setMarked(true);
                        view.getButtonAt(selRow, selCol).setText("X");
                        game.setNumMarks(game.getNumMarks() - 1);
                        view.changeBombsLeft(game.getNumMarks());
                    }
                }
            }
        }

        /**
         * Invoked when the mouse enters a component.
         *
         * @param e
         */
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        /**
         * Invoked when the mouse exits a component.
         *
         * @param e
         */
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    class MineListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == view.getRestart()) {
                game = new Minesweeper();
                view.enableBoard();
                view.resetButtons();
                firstClick = true;
            } else {
                findCell(e);
                if (!game.getGrid()[selRow][selCol].isMarked()) {
                    if (firstClick) {
                        game.placeMines(selRow, selCol);
                        firstClick = false;
                    }

                    if (game.getGrid()[selRow][selCol].isBomb()) {
                        game.getGrid()[selRow][selCol].setRevealed(true);
                        view.getButtonAt(selRow, selCol).setText("B");
                    }

                    game.revealCells(selRow, selCol);
                    view.revealButtons(game);
                    int winner = game.win();
                    if (winner == 2) {
                        view.setTitle("LOSE");
                        view.findBombs(game);
                        view.disableBoard();
                    } else if (winner == 1) {
                        view.setTitle("WIN");
                        view.findBombs(game);
                        view.disableBoard();
                    }

                    view.getButtonAt(selRow, selCol).setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    /*
    Finds the Cell that was pressed and sets its row and column value int our selRow and selCol variables
     */
    private void findCell(AWTEvent e) {
        for(int row = 0; row < game.getNumRows(); ++row) {
            for(int col = 0; col < game.getNumCols(); ++col) {
                if(e.getSource() == view.getButtonAt(row, col)) {
                    selRow = row;
                    selCol = col;
                }
            }
        }
    }
}
