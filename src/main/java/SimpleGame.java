import Entity.Enemy;
import Entity.MovingEnemy;
import Entity.Player;
import Entity.TrapEnemy;
import GameStates.GameStateManager;
import GameStates.MainMenuState;
import GameStates.RunningState;
import GameStates.DeathScreenState;
import Item.Items;
import Rewards.bonus;
import Rewards.regular;
import tile.TileManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;


public class SimpleGame extends JPanel {
    private GameStateManager stateManager;

    public SimpleGame() {

        stateManager = new GameStateManager();
        stateManager.setState(new MainMenuState());
    }

    public static void main(String[] args) throws IOException {
       new SimpleGame();
    }
}