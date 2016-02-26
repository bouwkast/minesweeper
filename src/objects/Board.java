package objects;

public class Board {
    private Cell grid[][];

    public Board() {
        grid = new Cell[10][10];
    }
    public Board(int width, int height) {
        grid = new Cell[width][height];
    }

    public Cell getCellAt(int row, int col) {
        return grid[row][col];
    }
}
