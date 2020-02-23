package gui;

import logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Observable;
import java.util.Observer;

public class GameBoard extends JPanel implements Observer, ConstGui {
    private Game game;

    public GameBoard(Game _game) {
        game = _game;
        game.addObserver(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.startPause();
                else if (e.getButton() == MouseEvent.BUTTON3)
                    game.resetBattlefield();
                game.playerMoved(e.getX() / CELL_DIM, e.getY() / CELL_DIM);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                game.playerMoved(e.getX() / CELL_DIM, e.getY() / CELL_DIM);
            }
        });

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        g.setColor(Color.white);
        Battlefield bf = game.getData().getBattlefield();
        g.fillRect(0, 0, CELL_DIM * bf.getCols(), CELL_DIM * bf.getRows());
        GameData data = game.getData();
        printCells(g, data.getBattlefield().getSpots());

        final PathCreate enemyPath = data.getEnemyPath();
        try {
            if (enemyPath != null)
                printCells(g, enemyPath.getPathSet(), new Color(220, 220, 220));
        } catch (ConcurrentModificationException ce) {
            // TODO: Fix thread errors
        }

        Player player = data.getPlayer();
        g.setColor(Color.blue);
        g.fillOval(player.getCol() * CELL_DIM, player.getRow() * CELL_DIM, CELL_DIM, CELL_DIM);

        Enemy enemy = data.getEnemy();
        g.setColor(Color.red);
        g.fillOval(enemy.getCol() * CELL_DIM, enemy.getRow() * CELL_DIM, CELL_DIM, CELL_DIM);
    }

    private void printCells(Graphics g, Iterable<Spot> spots, Color color) {
        Graphics2D g2 = (Graphics2D) g;

        if (spots == null)
            return;

        g2.setColor(color);

        for (Spot s : spots) {
            if (s == null) {
                continue;
            }
            g2.fillRect(s.getCol() * CELL_DIM,
                    s.getRow() * CELL_DIM,
                    CELL_DIM,
                    CELL_DIM);
        }
    }

    private void printCells(Graphics g, Collection<Spot> spots) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(Color.black);

        for (Spot s : spots) {
            if (s.isWall()) {
                g2.fillOval(s.getCol() * CELL_DIM,
                        s.getRow() * CELL_DIM,
                        CELL_DIM,
                        CELL_DIM);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
