package logic;

import java.util.ArrayList;

public class PathCreate implements Runnable, Constants {
    private boolean changed = false;

    private final Game game;
    private final Battlefield bf;
    private final Player player;
    private final Enemy enemy;

    private int moveCost;
    private ArrayList<Spot> pathSet;
    private ArrayList<Spot> openSet, closedSet;

    public PathCreate(Game _game, Battlefield _bf, Player _player, Enemy _enemy) {
        game = _game;
        bf = _bf;
        player = _player;
        enemy = _enemy;

        moveCost = 1;

        pathSet = new ArrayList<>();
        openSet = new ArrayList<>();
        closedSet = new ArrayList<>();
    }

    public ArrayList<Spot> getPathSet() {
        return pathSet;
    }

    public Spot getNextSpot() {
        Spot enemySpot = enemy.getSpot();
        try {
            int indPrev = pathSet.indexOf(enemySpot);
            pathSet.remove(indPrev);
            return pathSet.get(indPrev - 1);
        } catch (IndexOutOfBoundsException ioe) {
            return enemySpot;
        }
    }

    public void _setChanged() {
        this.changed = true;
    }

    public void run() {
        openSet.add(enemy.getSpot());
        Spot curSpot;

        while (game.getData().isRunning()) {
            if (changed) {
                changed = false;
                restartTrack();
            }
            curSpot = smallerFVal(openSet);
            if (curSpot == null) {
                game.getData().addMsgLog("F*** I'm locked -.-'");
                setPathAndNotify(null);
                break;
            }
            if (curSpot.equals(player.getSpot())) {
                setPathAndNotify(curSpot);
                break;
            }

            openSet.remove(curSpot);
            closedSet.add(curSpot);

            curSpot.setNeighbors(bf);

            for (Spot neighbor : curSpot.getNeighbors()) {
                if (closedSet.contains(neighbor) || neighbor.isWall())
                    continue;

                int tempGScore = curSpot.getGVal() + moveCost;

                if (!openSet.contains(neighbor))
                    openSet.add(neighbor);
                else if (tempGScore >= neighbor.getGVal())
                    continue;

                neighbor.setPrev(curSpot);
                neighbor.setGVal(tempGScore);
                neighbor.setHVal(heuristic(neighbor, player.getSpot()));
                neighbor.resetFVal();
            }

            setPathAndNotify(curSpot);
        }
    }

    private Spot smallerFVal(ArrayList<Spot> set) {
        if (set == null || set.size() < 1)
            return null;
        Spot smaller = set.get(0);
        for (int i = 1; i < set.size(); i++) {
            Spot spot = set.get(i);
            if (spot.getFVal() < smaller.getFVal())
                smaller = spot;
        }
        return smaller;
    }

    private synchronized void setPathAndNotify(Spot curSpot) {
        synchronized (this) {
            pathSet.clear();
            for (Spot spot = curSpot; spot != null; spot = spot.getPrev())
                pathSet.add(spot);
        }

        game._setChanged();
        game.notifyObservers();
    }

    private int heuristic(Spot sStart, Spot sEnd) {
        return (int) Math.sqrt(Math.pow(Math.abs(sEnd.getCol() - sStart.getCol()), 2) +
                Math.pow(Math.abs(sEnd.getRow() - sStart.getRow()), 2));
    }

    public void restartTrack() {
        pathSet.clear();
        openSet.clear();
        closedSet.clear();
        openSet.add(enemy.getSpot());
    }
}
