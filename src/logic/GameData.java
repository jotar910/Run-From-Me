package logic;

import java.util.PriorityQueue;
import java.util.Queue;

public class GameData implements Constants {
    private Game game;
    private Battlefield battlefield;
    private Player player;
    private Enemy enemy;

    private PathCreate enemyPath;
    private Thread tPath;
    private EnemyMovement enemyMovement;
    private Thread tEnemy;

    private Queue<String> msgLog;
    private boolean running;

    public GameData(Game _game) {
        game = _game;
        resetData();
    }

    public void addMsgLog(String msg) {
        msgLog.add(msg);
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public PathCreate getEnemyPath() {
        return enemyPath;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void movePlayer(int col, int row) {
        Spot[][] grid = battlefield.getGrid();
        player.setSpot(grid[row][col]);
        battlefield.restartSpots();
        if (tPath.getState() == Thread.State.TERMINATED) {
            enemyPath = new PathCreate(game, battlefield, player, enemy);
            tPath = new Thread(enemyPath);

            if (tEnemy.getState() == Thread.State.TERMINATED) {
                enemyMovement = new EnemyMovement(game, enemyPath, player, enemy);
                tEnemy = new Thread(enemyMovement);
                tPath.start();
                tEnemy.start();
            } else {
                enemyMovement.pathChanged(enemyPath);
                tPath.start();
            }

        } else {
            enemyPath._setChanged();
        }

        game._setChanged();
        game.notifyObservers();
    }

    public void printMsgLog() {
        String msg;
        while ((msg = msgLog.poll()) != null)
            System.out.println(msg);
    }

    public void stop() {
        if (running) {
            pause();
        }
        resetData();
    }

    public void pause() {
        try {
            if (running) {
                running = false;
                tPath.join();
                tEnemy.join();
            }

        } catch (InterruptedException e) {
            // TODO: Fix thread errors
        }
    }

    public void start() {

        enemyPath = new PathCreate(game, battlefield, player, enemy);
        tPath = new Thread(enemyPath);

        enemyMovement = new EnemyMovement(game, enemyPath, player, enemy);
        tEnemy = new Thread(enemyMovement);

        running = true;
        tPath.start();
        tEnemy.start();
    }

    private void resetData() {
        msgLog = new PriorityQueue<>();
        running = false;
        battlefield = new Battlefield(BF_ROWS, BF_COLS);
        Spot[][] grid = battlefield.getGrid();
        player = new Player(grid[battlefield.getRows() - 1][battlefield.getCols() - 1]);
        enemy = new Enemy(grid[0][0]);
    }
}
