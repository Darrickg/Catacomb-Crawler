package GamePanel;

import Entity.Enemy;
import Entity.MovingEnemy;
import Entity.Player;
import Entity.TrapEnemy;
import Item.Items;
import Rewards.bonus;
import Rewards.regular;
import tile.TileManager;
import HealthBar.HealthBar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    public TileManager tileManager;
    public Player player;
    public ArrayList<Enemy> enemies;
    public ArrayList<Items> items;
    public HealthBar healthBar;
    public long startingTime;

    public GamePanel() {
        // Initialize the running state.
        // adjust the TileManager accordingly
        tileManager = new TileManager(this, 60, 33, 32);

        // Set up player
        player = new Player(930, 410, 25,50,3);
        // Set up enemies
        enemies = new ArrayList<>();
        enemies.add(new MovingEnemy(500, 500, 30,14, 2, 10000));
        enemies.add(new TrapEnemy(400,400,30,17,75));
        items = new ArrayList<>();
        items.add(new regular(250,200,18,15,75));
        items.add(new bonus(300,300,16,16,200,100,500,tileManager));

        healthBar = new HealthBar();
        add(healthBar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        tileManager.draw((Graphics2D) g);

        // Draw player
        player.draw(g2d);

        // Draw enemies
        g.setColor(Color.BLUE);
        for (Enemy enemy : enemies) {
            //g.fillRect(enemy.getX(), enemy.getY(), 20, 20);
            if (enemy instanceof TrapEnemy) {
                if (!((TrapEnemy) enemy).isActivated()) {
                    enemy.draw(g2d);
                }
            } else {
                enemy.draw(g2d);
            }
        }
        for (Items item : items) {
            if (!item.isPickedUp()) {
                item.draw(g2d);
            }
        }
        for (int i = 0; i <= healthBar.getHealthIcons().length; i++) {
            if (i <= healthBar.getHealth()) {
                // Draw full heart
                g.drawImage(healthBar.getHealthIcons()[0], i * 30, 100, null);
            } else {
                // Draw empty heart
                g.drawImage(healthBar.getHealthIcons()[1], i * 30, 100, null);
            }
        }

        // Render the score on the screen
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Score: " + player.getScore(), 10, 30);

        // Render time
        long timeFromGameStart = System.currentTimeMillis() - startingTime;

        g2d.drawString("Time: " + timeFromGameStart / 1000 + "s", 10, 55);
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public long getStartingTime() {
        return startingTime;
    }
}
