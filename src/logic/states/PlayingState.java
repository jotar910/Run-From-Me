package logic.states;

import logic.Battlefield;
import logic.GameData;
import logic.Player;

public class PlayingState extends StateAdapter {
    public PlayingState(GameData data) {
        super(data);
    }

    @Override
    public IStates playerMoved(int col, int row) {
        GameData data = getData();
        Battlefield bf = data.getBattlefield();
        Player player = data.getPlayer();
        if (col > -1 && row > -1 &&
                col < bf.getCols() && row < bf.getRows() &&
                !bf.getGrid()[row][col].isWall() &&
                (col != player.getCol() || row != player.getRow())) {
            data.movePlayer(col, row);
        }
        return this;
    }

    @Override
    public IStates restartBattlefield() {
        GameData data = getData();
        data.stop();
        data.start();
        return this;
    }

    @Override
    public IStates startQuitGame() {
        getData().stop();
        return new BeginningState(getData());
    }

    @Override
    public IStates startPauseGame() {
        getData().pause();
        return new BeginningState(getData());
    }
}
