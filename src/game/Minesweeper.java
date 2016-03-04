package game;


import objects.Cell;

import java.util.Random;

//TODO Add a solver method to ensure that each generated board has its own unique solution

public class Minesweeper {

    private final int DEFAULT_VAL = 9; // default number of rows/cols
    private int numRows = DEFAULT_VAL, numCols = DEFAULT_VAL;
    private Cell grid[][]; // implementation of the board
    private int numMines, numMarks; // default amount will be

    public Minesweeper() {
        grid = new Cell[numRows][numCols];
        numMines = (int)(numRows * numCols * .15);
        numMarks = numMines;
        setupBoard();
    }
    public Minesweeper(int numRows, int numCols, int numMines) {
        if(numRows > 0 && numRows < 50)
            this.numRows = numRows;
        if(numCols > 0 && numCols < 50)
            this.numCols = numCols;
        grid = new Cell[this.numRows][this.numCols];
        this.numMines = numMines;
        this.numMarks = numMines;
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
        firstClickSetExcludes(excludeRow, excludeCol);
        int iterations = numMines;
        while(iterations > 0) {
            Random random = new Random();
            int row = random.nextInt(numRows);
            int col = random.nextInt(numCols);

            // Transfer over to a map/set/array for more efficiency
            if(!grid[row][col].isBomb() && !grid[row][col].isExclude()) {
                grid[row][col].setBomb(true);
                --iterations;
            }
        }
        calcNeighbors();
    }

    private void firstClickSetExcludes(int row, int col) {
        grid[row][col].setExclude(true);
        // up
        if(row - 1 > -1) {
            grid[row - 1][col].setExclude(true);
        }
        // up right
        if(row - 1 > -1 && col + 1 < numCols) {
            grid[row - 1][col + 1].setExclude(true);
        }
        // right
        if(col + 1 < numCols) {
            grid[row][col+1].setExclude(true);
        }
        // right down
        if(row + 1 < numRows && col + 1 < numCols) {
            grid[row+1][col+1].setExclude(true);
        }
        // down
        if(row + 1 < numRows) {
            grid[row+1][col].setExclude(true);
        }
        // down left
        if(row + 1 < numRows && col - 1 > -1) {
            grid[row+1][col-1].setExclude(true);
        }
        // left
        if(col - 1 > -1) {
            grid[row][col-1].setExclude(true);
        }
        // up left
        if(row - 1 > -1 && col - 1 > -1) {
            grid[row - 1][col - 1].setExclude(true);
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

    public void revealCells(int row, int col) {
        if(row < 0) return;
        if(col < 0) return;
        if(row >= numRows) return;
        if(col >= numCols) return;


        if(grid[row][col].isRevealed() || grid[row][col].isMarked() || grid[row][col].isBomb()) return;
        if(grid[row][col].getNeighborMines() > 0) {
            grid[row][col].setRevealed(true);
            return;
        }
        grid[row][col].setRevealed(true);
        revealCells(row - 1, col);
        revealCells(row + 1, col);
        revealCells(row, col - 1);
        revealCells(row, col + 1);
    }

    private void calcNeighbors() {
        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                // need to check 8 directions to see if there is a bomb in it
                if(grid[row][col].isBomb()) { // for each cell containing a bomb just increment all neighbor cells
                    // up
                    if(row - 1 > -1 && !grid[row - 1][col].isBomb()) {
                        grid[row - 1][col].incNeighborMines();
                    }
                    // up right
                    if(row - 1 > -1 && col + 1 < numCols && !grid[row - 1][col + 1].isBomb()) {
                        grid[row - 1][col + 1].incNeighborMines();
                    }
                    // right
                    if(col + 1 < numCols && !grid[row][col+1].isBomb()) {
                        grid[row][col+1].incNeighborMines();
                    }
                    // right down
                    if(row + 1 < numRows && col + 1 < numCols && !grid[row+1][col+1].isBomb()) {
                        grid[row+1][col+1].incNeighborMines();
                    }
                    // down
                    if(row + 1 < numRows && !grid[row + 1][col].isBomb()) {
                        grid[row+1][col].incNeighborMines();
                    }
                    // down left
                    if(row + 1 < numRows && col - 1 > -1 && !grid[row+1][col-1].isBomb()) {
                        grid[row+1][col-1].incNeighborMines();
                    }
                    // left
                    if(col - 1 > -1 && !grid[row][col-1].isBomb()) {
                        grid[row][col-1].incNeighborMines();
                    }
                    // up left
                    if(row - 1 > -1 && col - 1 > -1 && !grid[row - 1][col - 1].isBomb()) {
                        grid[row - 1][col - 1].incNeighborMines();
                    }
                }
            }
        }
    }

    public boolean areAllBombsMarked() {
        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                if(grid[row][col].isBomb() && !grid[row][col].isMarked()) {
                    return false; // loss
                }
            }
        }
        return true;
    }

    public boolean areAllCellsRevealed() {
        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                if(!grid[row][col].isBomb() && !grid[row][col].isRevealed()) {
                    return false; // loss
                }
            }
        }
        return true;
    }

    public int win() {
        for(int row = 0; row < numRows; ++row) {
            for(int col = 0; col < numCols; ++col) {
                if(grid[row][col].isBomb() && grid[row][col].isRevealed()) {
                    return 2; // loss
                }
                if(areAllCellsRevealed()) {
                    return 1; // win
                }
            }
        }
        return 0; // game not over
    }

    public int getNumMarks() {
        return numMarks;
    }

    public void setNumMarks(int numMarks) {
        this.numMarks = numMarks;
    }
}
