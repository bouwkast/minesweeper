package objects;

public class Cell {

    private boolean isBomb;
    private boolean isRevealed; // if the Cell has been revealed through selection or the flood fill
    private boolean isMarked; // if the Cell is marked for being a bomb
    private int neighborMines;
    private boolean isFirst; // if it is the first Cell to have been chosen, we don't want to put a bomb there

    public Cell(boolean isBomb) {
        this.isBomb = isBomb;
        isRevealed = false;
        isMarked = false;
        neighborMines = 0;
        isFirst = false;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public int getNeighborMines() {
        return neighborMines;
    }


    public void incNeighborMines() {
        neighborMines++;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
