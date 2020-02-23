package logic.states;

import logic.GameData;

public abstract class StateAdapter implements IStates {
    private GameData data;

    public StateAdapter(GameData _data) {
        data = _data;
    }

    public GameData getData() {
        return data;
    }

    @Override
    public IStates startQuitGame() {
        return this;
    }

    @Override
    public IStates startPauseGame() {
        return this;
    }

    @Override
    public IStates playerMoved(int col, int row) {
        return this;
    }

    @Override
    public IStates restartBattlefield() {
        return this;
    }
}
