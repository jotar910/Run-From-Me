package logic;

public class EnemyMovement implements Runnable, Constants {
    private final Game game;
    private PathCreate pathChanged;
    private PathCreate pathCreate;
    private Player player;
    private Enemy enemy;

    public EnemyMovement(Game _game, PathCreate _pathCreate, Player _player, Enemy _enemy) {
        game = _game;
        pathCreate = _pathCreate;
        player = _player;
        enemy = _enemy;
    }

    public void setPathCreate(PathCreate _pathCreate) {
        pathCreate = _pathCreate;
    }

    @Override
    public void run() {
        while (game.getData().isRunning()) {
            if (pathCreate == null)
                break;

            if (enemy.getSpot().equals(player.getSpot())) {
                continue;
            }

            if (pathChanged != null) {
                pathCreate = pathChanged;
                pathChanged = null;
            }

            Spot nextSpot = pathCreate.getNextSpot();
            enemy.setSpot(nextSpot);
            game._setChanged();
            game.notifyObservers();

            try {
                Thread.sleep(ENEMY_SLOWNESS);
            } catch (InterruptedException e) {
                // TODO: Fix thread errors
            }
        }
    }

    public void pathChanged(PathCreate enemyPath) {
        pathChanged = enemyPath;
    }
}
