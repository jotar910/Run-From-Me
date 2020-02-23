package gui;

import logic.Battlefield;
import logic.Game;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame implements ConstGui {

    public Gui(Game _game) {
        super("Run away!!");

        getContentPane().add(BorderLayout.CENTER, new MainPanel(_game));

        setLocation(20, 20);
        setSize(BF_COLS * CELL_DIM + 16,
                BF_ROWS * CELL_DIM + 39);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        validate();
    }
}
