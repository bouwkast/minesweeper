package game;


import objects.Cell;

import java.util.Random;

//TODO Add a solver method to ensure that each generated board has its own unique solution

public class Minesweeper {

    private final int DEFAULT_VAL = 9; // default number of rows/cols
    private int numRows = DEFAULT_VAL, numCols = DEFAULT_VAL;
    private Cell grid[][]; // implementation of the board
    private int numMines = 10, numMarks; // default amount will be

    /**
     * Default constructor, the grid size is 9x9, number of mines is 10
     * NOTE - bombs are not placed until after the first Cell clicked
     */
    public Minesweeper() {
        grid = new Cell[numRows][numCols];
        numMarks = numMines;
        setupBoard();
    }

    /**
     * Overloaded constructor, with variable size of grid size and the amount of mines.
     * Rows and cols must be greater than 0 and less than 50 or they will use the default values.
     * Mines must be 10 less than rows * cols to ensure that there is at least one cell that the player must find.
     *
     * @param numRows  is the number of rows
     * @param numCols  is the number of cols
     * @param numMines is the number of mines to be placed in the board.
     */
    public Minesweeper(int numRows, int numCols, int numMines) {
        setNumRows(numRows);
        setNumCols(numCols);
        grid = new Cell[this.numRows][this.numCols];
        setNumMines(numMines);
        this.numMarks = this.numMines;
        setupBoard();
    }

    /**
     * Gets the Cell at a specified position - there must be a Cell at the location otherwise there will be an exception
     *
     * @param row is the row of the Cell
     * @param col is the col of the Cell
     * @return the Cell at eth specified location, otherwise an IndexOutOfBoundsException
     */
    public Cell getCellAt(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            // Thrown an exception here
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    /*
     * This method creates each new Cell and makes sure that they don't have a bomb in them
     */
    private void setupBoard() {
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                grid[row][col] = new Cell(false); // set all Cells to have no bomb, bombs are set after first click
            }
        }
    }

    /**
     * Method that will randomly place mines throughout the board, except for the initial Cell pressed and all of
     * the Cells adjacent to it
     *
     * @param excludeRow the row of the first Cell pressed
     * @param excludeCol the col of the first Cell pressed
     */
    public void placeMines(int excludeRow, int excludeCol) {
        firstClickSetExcludes(excludeRow, excludeCol);
        int iterations = numMines;
        while (iterations > 0) {
            Random random = new Random();
            int row = random.nextInt(numRows);
            int col = random.nextInt(numCols);

            // Transfer over to a map/set/array for more efficiency
            if (!grid[row][col].isBomb() && !grid[row][col].isExclude()) {
                grid[row][col].setBomb(true);
                --iterations;
            }
        }
        calcNeighbors();
    }

    /*
        Sets the cell in the parameters to be excluded from being able to have a bomb, same with all adjacent Cells
     */
    private void firstClickSetExcludes(int row, int col) {
        grid[row][col].setExclude(true);
        // up
        if (row - 1 > -1) {
            grid[row - 1][col].setExclude(true);
        }
        // up right
        if (row - 1 > -1 && col + 1 < numCols) {
            grid[row - 1][col + 1].setExclude(true);
        }
        // right
        if (col + 1 < numCols) {
            grid[row][col + 1].setExclude(true);
        }
        // right down
        if (row + 1 < numRows && col + 1 < numCols) {
            grid[row + 1][col + 1].setExclude(true);
        }
        // down
        if (row + 1 < numRows) {
            grid[row + 1][col].setExclude(true);
        }
        // down left
        if (row + 1 < numRows && col - 1 > -1) {
            grid[row + 1][col - 1].setExclude(true);
        }
        // left
        if (col - 1 > -1) {
            grid[row][col - 1].setExclude(true);
        }
        // up left
        if (row - 1 > -1 && col - 1 > -1) {
            grid[row - 1][col - 1].setExclude(true);
        }

    }

    /**
     * Gets the number of rows in the grid
     *
     * @return the number of rows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Sets the number of rows for the grid, number of rows is greater than 0 and less than 50
     *
     * @param numRows the number of rows to set
     */
    public void setNumRows(int numRows) {
        if (numRows > 0 && numRows < 50)
            this.numRows = numRows;
        else this.numRows = DEFAULT_VAL;
    }

    /**
     * Gets the number of columns in the grid
     *
     * @return the number of columns
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Sets the number of columns for the grid, number of columns is greater than 0 and less than 50
     *
     * @param numCols the number of cols to set
     */
    public void setNumCols(int numCols) {
        if (numCols > 0 && numCols < 50)
            this.numCols = numCols;
        else this.numCols = DEFAULT_VAL;
    }

    /**
     * Gets the number of mines on the grid, this is a non-changing number unlike the number of marks/flags
     *
     * @return the number of mines on the grid
     */
    public int getNumMines() {
        return numMines;
    }

    /**
     * Sets the number of mines on the grid, number of mines must NOT be greater than or equal to the numRows*numCols - 10 and be greater than 1,
     * otherwise the number of mines will be set to either the default value or to numRows*numCols - 10
     *
     * @param numMines the number of mines to set
     */
    public void setNumMines(int numMines) {
        if (numMines > numRows * numCols - 10) {
            numMines = numRows * numCols - 10; // This is to ensure that there aren't too many mines
        }
        this.numMines = numMines;
    }

    /**
     * Gets the current grid of Cells
     *
     * @return a 2D array of Cells
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Recursive flood fill algorithm to reveal all appropriate Cells after each click
     *
     * @param row is the starting row
     * @param col is the starting col
     */
    public void revealCells(int row, int col) {
        if (row < 0) return;
        if (col < 0) return;
        if (row >= numRows) return;
        if (col >= numCols) return;


        if (grid[row][col].isRevealed() || grid[row][col].isMarked() || grid[row][col].isBomb()) return;
        if (grid[row][col].getNeighborMines() > 0) {
            grid[row][col].setRevealed(true);
            return;
        }
        grid[row][col].setRevealed(true);
        revealCells(row - 1, col);
        revealCells(row + 1, col);
        revealCells(row, col - 1);
        revealCells(row, col + 1);
    }

    /*
    Calculates the number of adjacent mines for all Cells
     */
    private void calcNeighbors() {
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                // need to check 8 directions to see if there is a bomb in it
                if (grid[row][col].isBomb()) { // for each cell containing a bomb just increment all neighbor cells
                    // up
                    if (row - 1 > -1 && !grid[row - 1][col].isBomb()) {
                        grid[row - 1][col].incNeighborMines();
                    }
                    // up right
                    if (row - 1 > -1 && col + 1 < numCols && !grid[row - 1][col + 1].isBomb()) {
                        grid[row - 1][col + 1].incNeighborMines();
                    }
                    // right
                    if (col + 1 < numCols && !grid[row][col + 1].isBomb()) {
                        grid[row][col + 1].incNeighborMines();
                    }
                    // right down
                    if (row + 1 < numRows && col + 1 < numCols && !grid[row + 1][col + 1].isBomb()) {
                        grid[row + 1][col + 1].incNeighborMines();
                    }
                    // down
                    if (row + 1 < numRows && !grid[row + 1][col].isBomb()) {
                        grid[row + 1][col].incNeighborMines();
                    }
                    // down left
                    if (row + 1 < numRows && col - 1 > -1 && !grid[row + 1][col - 1].isBomb()) {
                        grid[row + 1][col - 1].incNeighborMines();
                    }
                    // left
                    if (col - 1 > -1 && !grid[row][col - 1].isBomb()) {
                        grid[row][col - 1].incNeighborMines();
                    }
                    // up left
                    if (row - 1 > -1 && col - 1 > -1 && !grid[row - 1][col - 1].isBomb()) {
                        grid[row - 1][col - 1].incNeighborMines();
                    }
                }
            }
        }
    }

    /**
     * Checks to see if all non-bomb Cells have been revealed, if so then the players win
     *
     * @return whether all of the non-bomb Cells are revealed
     */
    public boolean areAllCellsRevealed() {
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                if (!grid[row][col].isBomb() && !grid[row][col].isRevealed()) {
                    return false; // loss
                }
            }
        }
        return true;
    }

    /**
     * Checks to see if any bomb Cells are revealed - if so then the game is over and the player loses.
     * Checks to see if all of the non-bomb Cells are revealed - if so then the game is over and the player wins.
     * If neither of those are true the game is still in progress
     *
     * @return 2 if the player loses
     * 1 if the player wins
     * 0 if the game is still in progress
     */
    public int win() {
        for (int row = 0; row < numRows; ++row) {
            for (int col = 0; col < numCols; ++col) {
                if (grid[row][col].isBomb() && grid[row][col].isRevealed()) {
                    return 2; // loss
                }
                if (areAllCellsRevealed()) {
                    return 1; // win
                }
            }
        }
        return 0; // game not over
    }

    /**
     * Gets the number of marks or flags that are left for the player to place on the board if they want to.
     *
     * @return the number of marks available
     */
    public int getNumMarks() {
        return numMarks;
    }

    /**
     * Sets the number of marks that the player has available, begins as the same number of bombs
     *
     * @param numMarks is the number of marks to set
     */
    public void setNumMarks(int numMarks) {
        this.numMarks = numMarks;
    }
}
