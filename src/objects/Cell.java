package objects;

public class Cell {

    private boolean isBomb;
    private boolean isRevealed;
    private boolean isMarked;
    private int neighborMines;
    private boolean isFirst;

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

    public void setNeighborMines(int neighborMines) {
        this.neighborMines = neighborMines;
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
