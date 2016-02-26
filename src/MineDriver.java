import control.MineController;
import game.Minesweeper;
import view.MineGUI;

public class MineDriver {

    public static void main(String[] args) {
        MineGUI theView = new MineGUI();

        Minesweeper theModel = new Minesweeper();

        MineController theController =
                new MineController(theView, theModel);

        theView.setVisible(true);
    }
}
