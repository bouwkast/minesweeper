package control;

import game.Minesweeper;
import view.MineGUI;

import java.awt.*;
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
        this.view.addMineListener(new MineListener());
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
            System.out.println(selRow + " " + selCol);
            if(firstClick) {
//                game.getGrid()[selRow][selCol].setFirst(true);
                game.placeMines(selRow, selCol);

                firstClick = false;
            }
            game.revealCells(selRow, selCol);
            view.findBombs(game);
            view.revealButtons(game);

            view.getButtonAt(selRow, selCol).setBackground(Color.LIGHT_GRAY);


        }

        private void findCell(ActionEvent e) {
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
}
