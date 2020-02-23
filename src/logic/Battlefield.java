package logic;

import java.util.ArrayList;

public class Battlefield {
    private int rows, cols;
    private Spot[][] grid;

    public Battlefield(int _rows, int _cols) {
        rows = _rows;
        cols = _cols;
        grid = new Spot[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                grid[i][j] = new Spot(i, j);
    }

    public Spot[][] getGrid() {
        return grid;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ArrayList<Spot> getSpots() {
        ArrayList<Spot> spots = new ArrayList<>();
        for (Spot[] sL : grid)
            for (Spot s : sL)
                spots.add(s);
        return spots;
    }

    public void restartSpots() {
        for (Spot[] sL : grid) {
            for (Spot s : sL) {
                s.resetValues();
            }
        }
    }
}
