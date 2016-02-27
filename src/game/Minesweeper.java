package game;


import objects.Cell;

import java.util.Random;

public class Minesweeper {

    private final int DEFAULT_VAL = 9;
    private int numRows = DEFAULT_VAL, numCols = DEFAULT_VAL;
    private Cell grid[][];
    private int numMines; // default amount will be

    public Minesweeper() {
        grid = new Cell[numRows][numCols];
        numMines = (int)(numRows * numCols * .8);
        setupBoard();
    }
    public Minesweeper(int numRows, int numCols) {
        if(numRows > 0 && numRows < 50)
            this.numRows = numRows;
        if(numCols > 0 && numCols < 50)
            this.numCols = numCols;
        grid = new Cell[this.numRows][this.numCols];
        numMines = (int)(numRows * numCols * .8);
        setupBoard();
    }

    public Cell getCellAt(int row, int col) {
        return grid[row][col];
    }

    private void setupBoard() {
        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                grid[row][col] = new Cell(false); // set all Cells to have no bomb, bombs are set after first click
            }
        }
    }

    public void placeMines(int excludeRow, int excludeCol) {
        int iterations = numMines;

        while(iterations > 0) {
            Random random = new Random();
            int row = random.nextInt(numRows);
            int col = random.nextInt(numCols);
            // Transfer over to a map/set/array for more efficiency
            if(!grid[row][col].isBomb() && !grid[row][col].isFirst()) {
                grid[row][col].setBomb(true);
                --iterations;
            }
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    public Cell[][] getGrid() {
        return grid;
    }

}
