package control;

import game.Minesweeper;
import view.MineGUI;

public class MineController {

    private Minesweeper game;
    private MineGUI view;

    public MineController(MineGUI view, Minesweeper game) {
        this.view = view;
        this.game = game;
    }
}
