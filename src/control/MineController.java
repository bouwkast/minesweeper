package control;

import game.Minesweeper;
import view.MineGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineController {

    private Minesweeper game;
    private MineGUI view;

    private int selRow, selCol;

    private boolean firstClick = true;

    public MineController(MineGUI view, Minesweeper game) {
        this.view = view;
        this.game = game;
    }

    class MineListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            findCell(e);
            if(firstClick) {
                game.getGrid()[selRow][selCol].setFirst(true);
                game.placeMines(selRow, selCol);
            }

        }

        private void findCell(ActionEvent e) {
            for(int row = 0; row < game.getNumRows(); ++row) {
                for(int col = 0; col < game.getNumCols(); ++col) {
                    if(e.getSource() == game.getGrid()[row][col]) {
                        selRow = row;
                        selCol = col;
                    }
                }
            }
        }
    }
}
