package logic;

import java.util.ArrayList;

public class Spot {
    private boolean wall;

    private int row, col;
    private int fVal, gVal, hVal;
    private ArrayList<Spot> neighbors;
    private Spot prev;

    public Spot(int _row, int _col) {
        row = _row;
        col = _col;
        fVal = gVal = hVal = 0;
        neighbors = new ArrayList<>();
        prev = null;

        wall = Math.random() < 0.2f;
    }

    public void resetValues() {
        fVal = gVal = hVal = 0;
        neighbors.clear();
        prev = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getFVal() {
        return fVal;
    }

    public int getGVal() {
        return gVal;
    }

    public int getHVal() {
        return hVal;
    }

    public Spot getPrev() {
        return prev;
    }

    public ArrayList<Spot> getNeighbors() {
        return neighbors == null ?
                new ArrayList<>() : // instead of null
                neighbors;
    }

    public boolean isWall() {
        return wall;
    }

    public void setPrev(Spot prev) {
        this.prev = prev;
    }

    public void setGVal(int gVal) {
        this.gVal = gVal;
    }

    public void setHVal(int hVal) {
        this.hVal = hVal;
    }

    public void setNeighbors(Battlefield bf) {
        if (!neighbors.isEmpty())
            return;

        Spot[][] grid = bf.getGrid();
        int rows = bf.getRows();
        int cols = bf.getCols();
        if (row > 0)
            neighbors.add(grid[row - 1][col]);
        if (row < rows - 1)
            neighbors.add(grid[row + 1][col]);
        if (col > 0)
            neighbors.add(grid[row][col - 1]);
        if (col < cols - 1)
            neighbors.add(grid[row][col + 1]);
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public void resetFVal() {
        fVal = hVal + gVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spot spot = (Spot) o;
        return row == spot.row &&
                col == spot.col;
    }

    @Override
    public int hashCode() {
        return row + col;
    }
}
