package gui;

import logic.Game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainPanel extends JPanel {
    private final Game game;
    private final GameBoard gameBoard;

    public MainPanel(Game _game) {
        game = _game;
        gameBoard = new GameBoard(game);

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, gameBoard);

        add(BorderLayout.CENTER, gameBoard);
    }
}
