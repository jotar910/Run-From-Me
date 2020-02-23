package logic;

import logic.states.BeginningState;
import logic.states.IStates;

import java.util.Observable;


public class Game extends Observable {
    private GameData data;
    private IStates state;

    public Game() {
        data = new GameData(this);
        state = new BeginningState(data);
    }

    public GameData getData() {
        return data;
    }

    public void _setChanged() {
        setChanged();
    }

    public void startQuit() {
        state = state.startQuitGame();
    }

    public void startPause() {
        state = state.startPauseGame();
    }

    public void playerMoved() {
        final Player player = data.getPlayer();
        state = state.playerMoved(player.getCol(), player.getRow());
    }

    public void playerMoved(int col, int row) {
        state = state.playerMoved(col, row);
    }

    public void resetBattlefield() {
        state = state.restartBattlefield();
    }
}
