package logic.states;

import logic.GameData;

public class BeginningState extends StateAdapter {
    public BeginningState(GameData data) {
        super(data);
    }

    @Override
    public IStates startQuitGame() {
        GameData data = getData();
        data.printMsgLog();
        data.start();
        return new PlayingState(data);
    }

    @Override
    public IStates startPauseGame() {
        return startQuitGame();
    }

    @Override
    public IStates restartBattlefield() {
        GameData data = getData();
        data.stop();
        data.start();
        return new PlayingState(data);
    }
}
