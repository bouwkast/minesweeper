import control.MineController;
import game.Minesweeper;
import view.MineGUI;

public class MineDriver {

    public static void main(String[] args) {
        Minesweeper theModel = new Minesweeper();
        MineGUI theView = new MineGUI(theModel);

        MineController theController =
                new MineController(theView, theModel);

        theView.setVisible(true);
    }
}
