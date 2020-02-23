package logic.states;

public interface IStates {
    IStates startQuitGame();
    IStates startPauseGame();
    IStates playerMoved(int col, int row);
    IStates restartBattlefield();
}
